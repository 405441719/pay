package com.payinxl.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Created by barry
 * Date:2017/3/14
 */
@Component
@ConfigurationProperties(prefix = "website")
public class WebsiteConfig{
        private String sitename;
        private String email;
        private String telphone;
        private String qq;
        private String qq2;
        private String copyright;

        public String getQq2() {
                return qq2;
        }

        public void setQq2(String qq2) {
                this.qq2 = qq2;
        }

        public String getCopyright() {
                return copyright;
        }

        public void setCopyright(String copyright) {
                this.copyright = copyright;
        }

        public String getSitename() {
                return sitename;
        }

        public void setSitename(String sitename) {
                this.sitename = sitename;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getTelphone() {
                return telphone;
        }

        public void setTelphone(String telphone) {
                this.telphone = telphone;
        }

        public String getQq() {
                return qq;
        }

        public void setQq(String qq) {
                this.qq = qq;
        }
}
