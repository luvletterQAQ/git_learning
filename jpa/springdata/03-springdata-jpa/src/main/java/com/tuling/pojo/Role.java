package com.tuling.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 * 多（用户）对多（角色）
 */
@Entity
@Table(name="tb_role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="role_name")
    private String rName;

    public Role(String rName) {
        this.rName = rName;
    }

    public Role(Long id, String rName) {
        this.id = id;
        this.rName = rName;
    }

    public Role() {
    }


    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> roles;
}
