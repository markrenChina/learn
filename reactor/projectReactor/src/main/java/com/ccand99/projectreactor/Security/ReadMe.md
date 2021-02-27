# Security

## Security基础知识

### 认证

void configure(AuthenticationManagerBuilder auth)

1. 基于内存用户认证（inMemoryAuthentication）
2. 基于数据库用户认证(jdbcAuthentication)
3. 基于LDAP服务器方式用户认证(ldapAuthentication)
4. 自定义用户认证（UserDetailsService）(适用于响应式)

自定义用户认证详细说明：

数据库映射实体类需要实现UserDetails接口，重写getAuthorities()方法返回用户权限集合，各种is方法返回Boolean。创建CRUD的Repository(定义findByUsername())。

服务层需要实现UserDetailsService接口

```java
public interface UserDetailsService {
    //对应创建CRUD的Repository的定义findByUsername（）
    UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;
}
```

PasswordEncode 接口实现：

1. BCryptPasswordEncoder: bcrypt强哈希加密
2. NoOpPasswordEncoder: 不进行转码
3. Pbkdf2PasswordEncoder： PBKDF2加密
4. SCryptPasswordEncoder: scrypt哈希加密
5. StandardPasswordEncoder: SHA-256哈希加密
6. 自定义实现passwordEncode接口

```java
public interface PasswordEncode {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword,String encodedPassword);
}
```

passwordEncode接口的实现，不是解码数据库密码，而是对输入进行加密，然后比对（matches（）方法）加密后的内容。

### 保护

void configure(HttpSecurity http) throws Exception

几种功能：

1. 对特定请求进行条件验证（接口权限配置）
2. 配置自定义登录页面
3. 支持用户退出应用
4. 预防跨站请求伪造

注意： 在实际代码规则声明中，前面的规则优先级高于后面的优先级（前面的规则先被匹配）

利用SpEL表达式和access（）能实现自定义权限。

csrf关闭方法： .and().csrf().disable()

### 审核

确定用户：

1. 注入Principal对象，从UserRepository中查找用户
2. 注入Authentication对象，调用getPrincipal()强制转为User
3. SecurityContextHolder，获取上下文，再获取Authentication对象
4. 接收时用@AuthenticationPrincipal注解标注方法user参数。

## 响应式

ReactiveSecurityContextHolder 依赖于 Reactor Context API

ReactiveSecurityContextHolder.getContext().block() 会获取一个空的上下文，因为上下文解析的策略是惰性的。

需要使用spring5为security提供的新新模块ReactorContextWebFilter，ReactorContextWebFilter#subcriberContext通过ServerSecurityContextRepository来执行解析

```java
public interface ServerSecurityContextRepository {
    Mono<Void> save(ServerWebExchange var1, SecurityContext var2);

    Mono<SecurityContext> load(ServerWebExchange var1);
}
```
