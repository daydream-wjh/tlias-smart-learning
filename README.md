# tlias（Tlias 智能学习辅助系统 · 后端练手项目）

## 项目简介

黑马程序员「Tlias 智能学习辅助系统」的 Java 后端实现，是一个典型的 **Spring Boot + MyBatis 单体 CRUD 教学项目**：提供部门管理、员工管理（分页/条件查询/批量删除）、登录鉴权（JWT）、图片上传（阿里云 OSS）以及事务与 AOP 的示例代码。

仓库是单模块结构（`pom.xml` + `src/main` + `src/test`），没有前端，也没有 README。它更像一份"跟着课程敲下来的练习代码"，里面同时保留了 `Filter` 与 `Interceptor` 两套登录校验示例、手写 AOP 日志切面（空实现）、dom4j 解析 XML 的 demo 等学习痕迹。

## 功能模块

| 模块 | 能力 | 代码位置 |
| --- | --- | --- |
| 部门管理 | 查询全部、按 id 查询、新增、修改、删除（删除后写操作日志） | `controller/DeptController.java` + `DeptServiceImpl` |
| 员工管理 | 分页 + 条件（姓名/性别/入职日期区间）查询、按 id 查询、新增、修改、批量删除 | `controller/EmpController.java` + `EmpServiceImpl` |
| 登录鉴权 | `POST /login` 校验用户名密码，签发 JWT；`LoginCheckInterceptor` 校验请求头 `token` | `controller/LoginController.java`、`interceptor/LoginCheckInterceptor.java` |
| 文件上传 | `POST /upload` 上传图片到阿里云 OSS 并返回访问 URL | `controller/UploadController.java`、`utils/AliOSSUtils.java` |
| 部门操作日志 | 删除部门时在当前事务外（`REQUIRES_NEW`）写入 `dept_log` | `service/impl/DeptLogServiceImpl.java` |
| 统一返回 / 分页 | `Result`、`PageBean` 两个通用模型 | `pojo/Result.java`、`pojo/PageBean.java` |
| 全局异常处理 | 统一捕获并返回 `Result.error` | `exception/GlobalExceptionHandler.java` |
| 未完成：员工操作日志 | `@Log` 注解、`OperateLog` 实体、`OperateLogMapper` 已就位，但切面 `aop/logAspect.java` 是**空文件** | `anno/Log.java`、`aop/logAspect.java` |

## 技术栈

全部依据 `pom.xml` 与 `src/main/resources/application.yml`：

| 类别 | 选型 | 版本 |
| --- | --- | --- |
| 语言 / 构建 | Java、Maven（含 Maven Wrapper） | 17 |
| 基础框架 | Spring Boot（parent） | 3.2.2 |
| Web | spring-boot-starter-web（内置 Tomcat，`jakarta.*` 命名空间） | 随 Boot |
| 持久层 | MyBatis（mybatis-spring-boot-starter，注解 + XML 混用） | 3.0.3 |
| 数据库 | MySQL（`com.mysql:mysql-connector-j`） | 随 Boot |
| 分页 | PageHelper | 2.1.0 |
| 对象存储 | 阿里云 OSS SDK（`aliyun-sdk-oss`） | 3.15.1 |
| JWT | jjwt | 0.9.1 |
| JSON | fastjson | 1.2.76 |
| AOP | spring-boot-starter-aop | 随 Boot |
| 监控 | spring-boot-starter-actuator | 随 Boot |
| XML 解析 | dom4j | 2.1.3 |
| 其它 | Lombok、spring-boot-configuration-processor、jaxb-api 2.3.1 / activation 1.1.1 / jaxb-runtime 2.3.3（OSS SDK 依赖） | — |
| 外部私有依赖 | `com.example:itheima-utils:0.0.1-SNAPSHOT` | 0.0.1-SNAPSHOT |

> 注意最后一行：`itheima-utils` 是课程提供的本地工具包，**不在 Maven 中央仓库**，`TliasApplication` 上的 `@EnableHeaderConfig` 注解就来自这个包（`import com.example.*`）。没有它，项目无法编译，详见「已知问题」。

## 模块说明

单模块项目，按包分层：

```
tlias-smart-learning/
├── pom.xml
├── mvnw / mvnw.cmd / .mvn/          Maven Wrapper
└── src/
    ├── main/
    │   ├── java/com/demo/tlias/
    │   │   ├── TliasApplication.java     启动类（@ServletComponentScan + @EnableHeaderConfig）
    │   │   ├── controller/               DeptController、EmpController、LoginController、UploadController
    │   │   ├── service/ + service/impl/  DeptService、EmpService、DeptLogService
    │   │   ├── mapper/                   DeptMapper、EmpMapper（注解 SQL）、DeptLogMapper、OperateLogMapper
    │   │   ├── pojo/                     Dept、Emp、DeptLog、OperateLog、Result、PageBean
    │   │   ├── config/                   WebConfig（注册拦截器）、CommonConfig（SAXReader Bean）、HeaderConfig（空类）
    │   │   ├── interceptor/              LoginCheckInterceptor（实际生效）
    │   │   ├── filter/                   LoginCheckFilter、DemoFilter（@WebFilter 已被注释，未生效）
    │   │   ├── aop/                      logAspect.java（空文件）
    │   │   ├── anno/                     @Log 注解
    │   │   ├── exception/                GlobalExceptionHandler
    │   │   └── utils/                    JwtUtils、AliOSSUtils、AliOSSProperties
    │   └── resources/
    │       ├── application.yml           数据源 / MyBatis / OSS 配置
    │       ├── com/demo/tlias/mapper/EmpMapper.xml   员工动态 SQL（与接口同包，MyBatis 自动加载）
    │       ├── 1.xml                     dom4j 解析 demo 用的样例文件
    │       └── static/upload.html        上传接口的简易测试页
    └── test/java/com/demo/tlias/         TliasApplicationTests、Demo（dom4j 练习）
```

依赖方向：`controller → service → mapper → MySQL`，`utils`/`pojo` 被各层共用。

## 数据库与配置

### 需要的外部依赖

| 组件 | 默认配置 | 用途 |
| --- | --- | --- |
| MySQL 8 | `jdbc:mysql://localhost:3306/tlias`，`root` / `root` | 业务库 |
| 阿里云 OSS | `oss-cn-shenzhen.aliyuncs.com`，bucket 名走环境变量 | 图片上传 |

### 表结构

从 Mapper（注解 SQL 与 XML）中实际出现的表名归纳，共 4 张：

| 表 | 说明 | 主要字段（据实体推断） |
| --- | --- | --- |
| `dept` | 部门 | `id`、`name`、`create_time`、`update_time` |
| `emp` | 员工 | `id`、`username`、`password`、`name`、`gender`、`image`、`job`、`entrydate`、`dept_id`、`create_time`、`update_time` |
| `dept_log` | 部门删除日志 | `id`、`create_time`、`description` |
| `operate_log` | 员工操作日志（功能未完成） | `id`、`operate_user`、`operate_time`、`class_name`、`method_name`、`method_params`、`return_value`、`cost_time` |

### 配置说明

- `spring.datasource.*`：数据库连接，默认 `localhost:3306/tlias`，账号 `root` + 弱口令，需按本机修改。
- `spring.servlet.multipart.max-file-size: 10MB`、`max-request-size: 100MB`：上传大小限制。
- `mybatis.configuration`：开启 SQL 控制台打印与驼峰映射。
- `aliyun.oss.*`：OSS 的 endpoint / AK / SK / bucketName，由 `AliOSSProperties`（`@ConfigurationProperties`）读取，`AliOSSUtils` 消费。**AK/SK 已改为环境变量**（`ALIYUN_OSS_AK` / `ALIYUN_OSS_SK` / `ALIYUN_OSS_BUCKET`），仓库里不再有任何真实凭据。
- 文件末尾还有一个自定义键 `name: wjh`（作者名，代码中未见引用）。

### 建表脚本

**仓库未包含 SQL 脚本**（无 `.sql` 文件）。`tlias` 库与 4 张表需要自己按上表建好，否则登录与列表接口会直接报 `Table 'tlias.emp' doesn't exist`。

## 快速开始

```bash
# 0. 环境：JDK 17、Maven 3.8+、MySQL 8
# 1. 建库建表（仓库无脚本，需自行准备 dept / emp / dept_log / operate_log）
mysql -uroot -p -e "CREATE DATABASE tlias DEFAULT CHARSET utf8mb4;"

# 2. 关键前置：安装课程提供的私有依赖 itheima-utils（否则编译失败）
#    该 jar 不在中央仓库，需自行取得后执行：
mvn install:install-file -Dfile=itheima-utils-0.0.1-SNAPSHOT.jar \
  -DgroupId=com.example -DartifactId=itheima-utils -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

# 3. 修改 src/main/resources/application.yml 的数据库与 OSS 配置
# 4. 编译启动（默认端口 8080）
./mvnw spring-boot:run
#   或 ./mvnw clean package -DskipTests && java -jar target/tlias-0.0.1-SNAPSHOT.jar

# 5. 登录拿 token，之后所有请求带 header： token: <jwt>
curl -X POST http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"<用户名>","password":"<密码>"}'
```

## 接口文档

无 Swagger/Knife4j，接口以 Controller 注解为准：

| 方法 | 路径 | 用途 |
| --- | --- | --- |
| POST | `/login` | 员工登录，返回 JWT 字符串 |
| GET | `/depts` | 查询全部部门 |
| GET | `/depts/{id}` | 按 id 查询部门 |
| POST | `/depts` | 新增部门 |
| PUT | `/depts` | 修改部门 |
| DELETE | `/depts/{id}` | 删除部门（同时写 `dept_log`） |
| GET | `/emps?page=1&pageSize=10&name=&gender=&begin=&end=` | 员工分页 + 条件查询 |
| GET | `/emps/{id}` | 按 id 查询员工 |
| POST | `/emps` | 新增员工 |
| PUT | `/emps` | 修改员工 |
| DELETE | `/emps/{ids}` | 批量删除（`/emps/1,2,3`） |
| POST | `/upload` | 上传图片到 OSS，表单字段名 `image`，返回 URL |

统一返回结构：`{"code":1,"msg":"success","data":...}`，失败为 `{"code":0,"msg":"..."}`；未登录返回 `{"code":0,"msg":"NOT_LOGIN"}`。

## 已知问题

> 保留历史问题原文；已修掉的条目在末尾标 **（已修复）** 并说明新做法，未标记的仍然存在。汇总见文末「修复记录」。

1. **编译阻塞：缺少私有依赖**：`pom.xml` 依赖 `com.example:itheima-utils:0.0.1-SNAPSHOT`，该构件不在任何公共仓库；`TliasApplication` 的 `@EnableHeaderConfig` 也从该包导入，`config/HeaderConfig.java` 只是同名空类占位。没有这个 jar，`mvn compile` 直接失败。（未修复）
2. **硬编码阿里云 AK/SK**（已修复）：原来的 `application.yml` 里明文写着真实的 `accessKeyId` / `accessKeySecret` 和 bucket 名。现已改为 `${ALIYUN_OSS_AK:...}` 形式的环境变量占位；**同时用 `git filter-branch` 把这对密钥从全部历史提交里抹掉了**，避免翻旧提交还能拿到。如果你曾经用过这对密钥，仍建议去阿里云控制台轮换一次。
3. **数据库口令硬编码**（已修复）：改为 `${DB_USERNAME:root}` / `${DB_PASSWORD:root}`，默认值仍是本地开发的 root，但不再写死在配置里。
4. **JWT 密钥硬编码且过期时间写死**：`JwtUtils` 里 `signKey` 是一个写死的短英文单词常量，`genJwt` 中过期时间写死 `System.currentTimeMillis() + 3600 * 1000`（1 小时），而字段 `expire = 43200000L`（12 小时）定义了却没使用，两者不一致。（未修复）
5. **员工操作日志功能未实现**：`aop/logAspect.java` 是 **0 行的空文件**，`@Log` 注解已标在 `EmpController.delete` 上但没有任何切面处理，`operate_log` 表永远不会被写入。（未修复）
6. **`EmpMapper.xml` 的动态更新有 SQL 隐患**：`<if>` 条件里出现 `test="name !=null and username!=''"`（判断 `name` 却校验 `username`）；除 `update_time` 外每个字段后面都跟了逗号，若 `updateTime` 为空而其它字段有值，拼出的 SQL 会以 `, where id=?` 结尾导致语法错误。**（已修复）** `update` 语句改为用 `<set>` 包裹：`<set>` 会自动去掉末尾多余逗号，所有条件都不成立时也不会生成空 `set`；`name` 分支的笔误改为 `test="name != null and name != ''"`。`delete`、`list` 两条语句未动（`list` 本来就用 `<where>`）。
7. **无参数校验**：登录、新增员工等接口直接接收实体，没有 `@Valid`/`@NotBlank`，空值会直接进 SQL。（未修复）
8. **缺少 SQL 脚本**：4 张表无建表语句、无初始化数据，`dept_log`、`operate_log` 的结构只能从实体推测。（未修复）
9. **学习痕迹与残留文件**：`src/main/resources/1.xml`（dom4j 样例）、`static/upload.html`（上传测试页）、`test/.../Demo.java`（dom4j 练习）、`filter/DemoFilter.java`（`@WebFilter` 被注释的示例）都属于课程练习残留；`filter/LoginCheckFilter` 与 `interceptor/LoginCheckInterceptor` 两份逻辑重复，只有拦截器生效（过滤器上的 `@WebFilter` 已注释）。（未修复）
10. **无 README、无接口文档、无测试**：原仓库没有任何说明文档，也没有 Swagger 依赖；`TliasApplicationTests` 只是空的上下文加载测试。（README 已补，其余未修复）
11. **日志配置不安全**：`logging.level.springframework.jdbc: DEBUG/TRACE` 会把 SQL 与参数打到控制台，生产环境需关掉。（未修复）

## 修复记录

| 文件 | 修了什么 | 现在的做法 |
| --- | --- | --- |
| `src/main/resources/com/demo/tlias/mapper/EmpMapper.xml` | `update` 每个 `<if>` 自带尾逗号，最后一个条件不成立时拼出 `... , where id=?`，SQL 语法错误 | 整段用 `<set>` 包裹，多余逗号由 MyBatis 自动裁掉，条件全不成立时也不会出现空 `set` |
| `src/main/resources/com/demo/tlias/mapper/EmpMapper.xml` | `test="name !=null and username!=''"` 条件笔误（判断 `name` 却校验 `username`） | 改为 `test="name != null and name != ''"` |
| `src/main/resources/com/demo/tlias/mapper/EmpMapper.xml` | `delete` / `list` 语句 | **未改动** |

