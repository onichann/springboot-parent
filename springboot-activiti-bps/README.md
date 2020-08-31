
## 数据表的命名规则

> Activiti的表都以act_开头，第二部分是表示表的用途的两个字母缩写标识，用途也和服务的API对应。

* act_hi_*：'hi’表示 history，此前缀的表包含历史数据，如历史(结束)流程实例，变量，任务等等。
* act_ge_*：'ge’表示 general，此前缀的表为通用数据，用于不同场景中。
* act_evt_*：'evt’表示 event，此前缀的表为事件日志。
* act_procdef_*：'procdef’表示 processdefine，此前缀的表为记录流程定义信息。
* act_re_*：'re’表示 repository，此前缀的表包含了流程定义和流程静态资源(图片，规则等等)。
* act_ru_*：'ru’表示 runtime，此前缀的表是记录运行时的数据，包含流程实例，任务，变量，异步任务等运行中的数据。Activiti只在流程实例执行过程中保存这些数据，在流程结束时就会删除这些记录。

## 数据表分类

* 通用数据(act_ge_)

| 表名 | 解释 |
| ------ | ------ |
| act_ge_bytearray | 二进制数据表，存储通用的流程定义和流程资源。 | 
| act_ge_property | 系统相关属性，属性数据表存储整个流程引擎级别的数据，初始化表结构时，会默认插入三条记录。 |

* 流程定义表(act_re_)

| 表名 | 解释 |
| ------ | ------ |
| act_re_deployment |部署信息表| 
| act_re_model |流程设计模型部署表|
| act_re_procdef |流程定义数据表|

* 运行实例表(act_ru_)

| 表名 | 解释 |
| ------ | ------ |
|act_ru_deadletter_job|	作业死亡信息表，作业失败超过重试次数|
|act_ru_event_subscr|	运行时事件表|
|act_ru_execution	|运行时流程执行实例表|
|act_ru_identitylink	|运行时用户信息表|
|act_ru_integration	|运行时积分表|
|act_ru_job	|运行时作业信息表|
|act_ru_suspended_job	|运行时作业暂停表|
|act_ru_task	|运行时任务信息表|
|act_ru_timer_job	|运行时定时器作业表|
|act_ru_variable|	运行时变量信息表|

* 历史流程表(act_hi_)

| 表名 | 解释 |
| ------ | ------ |
|act_hi_actinst	|历史节点表|
|act_hi_attachment	|历史附件表|
|act_hi_comment|	历史意见表|
|act_hi_detail|	历史详情表，提供历史变量的查询|
|act_hi_identitylink	|历史流程用户信息表|
|act_hi_procinst|	历史流程实例表|
|act_hi_taskinst|	历史任务实例表|
|act_hi_varinst|	历史变量表|

* 其他表

| 表名 | 解释 |
| ------ | ------ |
|act_evt_log	|流程引擎的通用事件日志记录表|
|act_procdef_info|	流程定义的动态变更信息|