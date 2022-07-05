1、安装

```
tar -zxvf prometheus-2.34.0.linux-amd64.tar.gz
./prometheus
```

2、配置文件

prometheus.yml

```yml
# my global config
global:
  scrape_interval:     15s # (设置抓取(pull)时间间隔，默认是1分钟）
  evaluation_interval: 15s # (设置rules评估时间间隔，默认是1分钟）
  # scrape_timeout is set to the global default (10s).
 
#（告警管理配置,无特殊需要默认就行）
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      # - alertmanager:9093
 
#加载rules,并根据设置的时间间隔定期评估,无特殊需要默认就行
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"
 
# (监控目标配置):
# (默认只有普罗米修斯自己的监控).
# 可以在scrape_configs配置中配置监控任务
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'service-order' 
    metrics_path: '/actuator/prometheus' 
    static_configs: 
    - targets: ['localhost:6001']
  - job_name: 'prometheus'
    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.
    static_configs:
    - targets: ['localhost:9090']

```

3、项目配置pom.xml

```xml
<dependency> 
    <groupId>org.springframework.boot</groupId> 
    <artifactId>spring-boot-starter-actuator</artifactId> 
</dependency> 
<dependency> 
    <groupId>io.micrometer</groupId> 
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

4、配置application

```properties
management.endpoint.metrics.enabled=true
management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
```

