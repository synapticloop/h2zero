# QUESTIONS

Questions are simple SQL statements that return one, and only one boolean (true/false) value.

This is a light-weight way to get a boolean answer to a question without using select beans or counters

The key `questions` is a JSON Array on the `table` JSON object, for example:

```
"tables": [
	{
		"fields": [
			...
		],
		"questions": [
			...
		]
	}
]
```

## Key Value Pairs

| key name | key value type | usage| notes |
|----------|----------------|------|-------|
| **name** | String | mandatory | This is the name for the question |
| **selectClause** | String | mandatory | This is the SQL query that is run which **MUST** return one and only one boolean result |
| **selectFields** | String | invalid | Select fields are ignored as there is only one select field which is of type boolean.  This will generate a validation (WARN) message |
| **whereClause** | String | optional | Used if the SQL query is parameterised |
| **whereFields** | String | optional | The name and type for each of the whereClause parameters |
| **unique** | String | ignored | This always returns one, and only one result in the result set. |

## Example 1: Simple Question
```
"questions": [
	{
		"name": "doWeHaveMoreThanTwentyUsers",
		"selectClause": "select count(*) > 20 from user"
	}
]
```

Whilst rather contrived, this will return a true/false answer as to whether we have more than 20 users.

You may also use a `whereClause` and `whereFields` if you wish to pass in additional parameters.

## Example 2: Parameterised Question

```
"questions": [
	{
		"name": "doesUserNameExist",
		"selectClause": "select count(*) > 0 from user",
		"whereClause": "where nm_user = ?",
		"whereFields": [
			"nm_user"
		]
	}
]
```
In the above example, the question being asked is whether a particular username exists in the user table.  If it does, the return value is `true` else `false`.