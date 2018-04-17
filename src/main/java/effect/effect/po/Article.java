package effect.effect.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Beldon
 * @create 2018-01-22 下午3:14
 */
@Entity
@Table(name = "t_article")
@Data
public class Article {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 32)
    private String id;

    /**
     * 用户id
     */
    @Column(length = 32)
    private String uid;

    /**
     * 分类id
     */
    @Column(length = 32,name = "cat_id")
    private String catId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @Column(columnDefinition = "text")
    private String content;

    /**
     * 阅读量
     */
    private Long views;

    /**
     * 创建时间
     */
    @Column(name = "cre_time")
    private Date creTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;


}
