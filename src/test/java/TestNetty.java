import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import org.junit.Test;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 20:39 2019/3/30
 */
public class TestNetty {
    @Test
    public void testProcessors() {
        int result = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(result);
    }

}
