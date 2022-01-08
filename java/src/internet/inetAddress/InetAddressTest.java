package internet.inetAddress;

import java.io.IOException;
import java.net.InetAddress;

/**
 * This program demonstrates the InetAddress class. Supply a host name as command-line
 * argument, or run without command-line arguments to see the address of the local host.
 *
 * @author Cay Horstmann
 * @version 1.02 2012-06-05
 */
public class InetAddressTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0) {
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for (InetAddress a : addresses)
                System.out.println(a);
        } else {
            InetAddress localHostAddress = InetAddress.getLocalHost();
            System.out.println(localHostAddress);
        }
        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
        System.out.println(addresses.length);
        for (InetAddress address : addresses) {
            System.out.println(address.getHostAddress());
        }
        while (true) {
            InetAddress address = InetAddress.getByName("www.baidu.com");
            System.out.println(address.getHostAddress());
            Thread.sleep(3000);
        }

    }
}
