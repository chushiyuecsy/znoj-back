# 数据库初始化
# @author <a href="https://github.com/Chushiyuecsy">程序员狗哥</a>
# @from <a href="https://1098756598.qzone.qq.com/">狗哥的企鹅空间</a>

-- 创建库
drop database if exists znoj;
create database if not exists znoj;

-- 切换库
use znoj;

-- 用户表
create table if not exists user
(
    userId       bigint       auto_increment            comment 'userId' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/teacher/admin',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_id (userId)
) comment '用户' collate = utf8mb4_unicode_ci;


-- 题目表
create table if not exists question
(
    questionId         bigint auto_increment comment 'questionId' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '题面',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    judgeCase  text                               null comment '输入输出用例（json 数组）[{"input": "1 2", "output": "1 2"}, {"input": "1 2", "output": "1 2"}]',
    judgeConfig text                              null comment '时间空间限制（json 对象）{"timeLimit": 1000, "memoryLimit": 1000}',
    submitNum  int      default 0                 not null comment '提交数',
    acceptedNum  int    default 0                 not null comment '通过数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (questionId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 题目提交表
create table if not exists run_submit
(
    runId      bigint auto_increment comment 'runId' primary key,
    questionId bigint                             not null comment '题目 id',
    userId     bigint                             not null comment '创建用户 id',
    language   varchar(128)                       null comment '编程语言',
    code       text                               null comment '代码 result为7时为ce信息',
    result     int                                null comment '运行结果(0ready, 1ac, 2tle, 3mle, 4wa, 5re, 6ole, 7ce, 8run&judge)',
    otherInfo  text                               not null comment '其他信息（json 对象）{acCase:10, time: 637, memory: 12592}',
    submitTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId),
    index idx_questionId (questionId)
) comment '提交表';

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';

-- 插入必要数据
insert into user (userAccount, userPassword, userName, userAvatar, userProfile, userRole)
values ('admin', '12345678', '赵宁', 'https://avatar.csdnimg.cn/2023/03/01/36101-1680301228000-1.jpg',
        '我是管理员', 'admin');
insert into user (userAccount, userPassword, userName, userAvatar, userProfile, userRole)
values ('teacher', '12345678', '魅魔', 'https://avatar.csdnimg.cn/2023/03/01/36101-1680301228000-1.jpg',
        '我是魅魔老师', 'teacher');
insert into user (userAccount, userPassword, userName, userAvatar, userProfile, userRole)
values ('test1', '12345678', '测试用户1', 'https://avatar.csdnimg.cn/2023/03/01/36101-1680301228000-1.jpg',
        '测试用户1', 'user');
insert into user (userAccount, userPassword, userName, userAvatar, userProfile, userRole)
values ('test2', '12345678', '测试用户2', 'https://avatar.csdnimg.cn/2023/03/01/36101-1680301228000-1.jpg',
        '测试用户2', 'user');
insert into user (userAccount, userPassword, userName, userAvatar, userProfile, userRole)
values ('test3', '12345678', '测试用户3', 'https://avatar.csdnimg.cn/2023/03/01/36101-1680301228000-1.jpg',
        '测试用户3', 'user');
insert into user (userAccount, userPassword, userName, userAvatar, userProfile, userRole)
values ('test4', '12345678', '测试用户4', 'https://avatar.csdnimg.cn/2023/03/01/36101-1680301228000-1.jpg',
        '测试用户4', 'user');

-- 插入题目
insert into question (title, content, tags, judgeCase, judgeConfig, userId)
values ('简单加法', '请实现两个数的加法。', '["math"]', '[{"input": "1 2", "output": "3"}, {"input": "1 2", "output": "3"}]',
        '{"timeLimit": 1000, "memoryLimit": 64}', 1);
insert into question (title, content, tags, judgeCase, judgeConfig, userId)
values ('简单减法', '请实现两个数的减法。', '["math"]', '[{"input": "1 2", "output": "-1"}, {"input": "1 2", "output": "-1"}]',
        '{"timeLimit": 1000, "memoryLimit": 64}', 1);
insert into question (title, content, tags, judgeCase, judgeConfig, userId)
values ('简单乘法', '请实现两个数的乘法。', '["math"]', '[{"input": "1 2", "output": "2"}, {"input": "1 2", "output": "2"}]',
        '{"timeLimit": 1000, "memoryLimit": 64}', 1);
insert into question (title, content, tags, judgeCase, judgeConfig, userId)
values ('简单除法', '请实现两个数的除法。', '["math"]', '[{"input": "1 2", "output": "0.5"}, {"input": "1 2", "output": "0.5"}]',
        '{"timeLimit": 1000, "memoryLimit": 64}', 1);