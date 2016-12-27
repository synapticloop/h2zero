
# h2Zero generation

## gradle plugin

Assuming that you have included the plugin

```
h2zero {
	inFile = 'src/test/resources/sample.h2zero'
	outDir = '.'
	verbose = 'false'
}
```

## ant

assuming that you have added the dependency above to the `runtime` configuration

```
task h2zero << {
	ant.taskdef(resource: 'h2zero.properties',
				classpath: configurations.runtime.asPath) {
	}

	ant.h2zero(inFile: 'src/main/resources/sample.h2zero',
				outDir: '.',
				verbose: 'false') {
	}
}
```

## Command line generation

try

```
  java -jar h2zero-all.jar
```

which will output:
