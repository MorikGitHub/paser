package cn.morik.log.paser;

import com.lmax.disruptor.EventFactory;

/**
 * @Description
 * @ClassName FtpFactory
 * @Author Morik
 * @date 2020.09.28 11:31
 */
public class FtpFactory implements EventFactory<DataInfo> {

    @Override
    public DataInfo newInstance() {
        return new DataInfo();
    }
}
