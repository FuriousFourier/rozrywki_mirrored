package pl.edu.agh.reporting.scheduling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MoFFTPConfiguration {

    @Value(value = "${mof.ftp.address}")
    public String address;
    @Value(value = "${mof.ftp.port}")
    public int port;
    @Value(value = "${mof.ftp.username}")
    public String username;
    @Value(value = "${mof.ftp.password}")
    public String password;

}
