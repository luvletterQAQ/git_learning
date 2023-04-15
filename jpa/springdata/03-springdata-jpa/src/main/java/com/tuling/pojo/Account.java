package com.tuling.pojo;

import lombok.*;

import javax.persistence.*;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 * 一对一
 * 一个客户对一个账户
 */
@Entity
@Table(name="tb_account")
@Data
/*@Getter         //  生成所有属性的get方法
@Setter        //  生成所有属性的set方法
@RequiredArgsConstructor  //  生成final属性的构造函数， 如果没有final就是无参构造函数
@EqualsAndHashCode*/
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
