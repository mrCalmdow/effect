package effect.effect.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Beldon
 * @create 2018-01-22 下午3:11
 */
@Entity
@Table(name = "t_user")
@Data
public class User {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 32)
    private String uid;

    /**
     * 登录账号
     */
    private String account;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别，1男，2女，0未知
     */
    private Integer gender;

    /**
     * email
     */
    private String email;

    /**
     * 邮箱状态，true-已激活，false-未激活
     */
    @Column(name = "email_status")
    private Boolean emailStatus;


    /**
     * 注册时间
     */
    @Column(name = "reg_time")
    private Date regTime;
}
