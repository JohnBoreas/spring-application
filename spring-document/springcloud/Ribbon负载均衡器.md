**RibbonClientConfiguration**





```java
@Bean
@ConditionalOnMissingBean
public IRule ribbonRule(IClientConfig config) {
	if (this.propertiesFactory.isSet(IRule.class, name)) {
		return this.propertiesFactory.get(IRule.class, config, name);
	}
    // 默认
	ZoneAvoidanceRule rule = new ZoneAvoidanceRule();
	rule.initWithNiwsConfig(config);
	return rule;
}
```

