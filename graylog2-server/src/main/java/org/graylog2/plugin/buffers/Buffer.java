/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog2.plugin.buffers;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import org.graylog2.plugin.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *     这个是ringBuffer的一个简单
 * </p>
 * @author Lennart Koopmann <lennart@socketfeed.com>
 */
public abstract class Buffer {
    private static final Logger log = LoggerFactory.getLogger(Buffer.class);

    /**
     * <p>
     *     RingBuffer的常见使用方法
     * </p>
     */
    protected RingBuffer<MessageEvent> ringBuffer;

    protected int ringBufferSize;

    public boolean isEmpty() {
        return getUsage() == 0;
    }

    public long getRemainingCapacity() {
        return ringBuffer.remainingCapacity();
    }

    public int getRingBufferSize() {
        return ringBufferSize;
    }

    public long getUsage() {
        if (ringBuffer == null) {
            return 0;
        }
        return (long) ringBuffer.getBufferSize() - ringBuffer.remainingCapacity();
    }

    protected void insert(Message message) {
        long sequence = ringBuffer.next();
        MessageEvent event = ringBuffer.get(sequence);
        event.setMessage(message);
        ringBuffer.publish(sequence);

        afterInsert(1);

    }

    /**
     * <p>
     *     获取等待的策略
     * </p>
     * @param waitStrategyName
     * @param configOptionName
     * @return
     */
    protected WaitStrategy getWaitStrategy(String waitStrategyName, String configOptionName) {
        switch (waitStrategyName) {
            case "sleeping":
                return new SleepingWaitStrategy();
            case "yielding":
                return new YieldingWaitStrategy();
            case "blocking":
                return new BlockingWaitStrategy();
            case "busy_spinning":
                return new BusySpinWaitStrategy();
            default:
                log.warn("Invalid setting for [{}]:"
                                + " Falling back to default: BlockingWaitStrategy.", configOptionName);
                return new BlockingWaitStrategy();
        }
    }

    protected abstract void afterInsert(int n);

    protected void insert(Message[] messages) {
        int length = messages.length;
        long hi = ringBuffer.next(length); // 获取下一个可用空间序号
        long lo = hi - (length - 1);
        for (long sequence = lo; sequence <= hi; sequence++) {
            MessageEvent event = ringBuffer.get(sequence); // 获取数据
            event.setMessage(messages[(int)(sequence - lo)]);
        }
        ringBuffer.publish(lo, hi); //通知发送
        afterInsert(length);
    }
}
