# COUNTERS

Counters are simple SQL statements that return one, and only one integer value.

This is a light-weight way to get a count of something without going through a selectClause bean finder.

The key `counters` is a JSON Array on the `table` JSON object, for example:

```
"tables": [
	{
		"fields": [
			...
		],
		"counters": [
			...
		]
	}
]
```

## Key Value Pairs

| key name | key value type | usage| notes |
|----------|----------------|------|-------|
| **name** | String | mandatory | This is the name for the question |
| **selectClause** | String | mandatory | This is the SQL query that is run which **MUST** return one and only one integer result |
| **selectFields** | String | invalid | Select fields are ignored as there is only one select field which is of type boolean.  This will generate a validation (WARN) message. |
| **whereClause** | String | optional | Used if the SQL query is parameterised |
| **whereFields** | String | optional | The name and type for each of the whereClause parameters |
| **unique** | String | ignored | This always returns one, and only one result in the result set.  This will generate a validation (WARN) message. |

## Example 1: Simple Count
```
"questions": [
	{
		"name": "countNumberOfUsers",
		"selectClause": "select count(*) from user"
	}
]
```

As it would appear, this would return the number of users in the table.

You may also use a `whereClause` and `whereFields` if you wish to pass in additional parameters.

## Example 2: Parameterised Count

```
"questions": [
	{
		"name": "countNumberOfUsersOverAge",
		"selectClause": "select count(*) from user"
		"whereClause": "where num_user_age > ?",
		"whereFields": [
			"num_user_age"
		]
	}
]
```

In the above example, the count being given is the number of users that are older than a parameterised value that is passed in.

