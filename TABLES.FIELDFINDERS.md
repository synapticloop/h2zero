# FIELDFINDERS

FieldFinders are simple finders that automatically generate a Finder with the minimal amount of SQL.

This is a light-weight way to to do a finder without defining a full finder in the `finders` section.

The key `fieldFinders` is a JSON Array on the `table` JSON object which contains a JSON object:

```
"tables": [
	{
		"fields": [
			...
		],
		"fieldFinders": [
			...
		]
	}
]
```

## Key Value Pairs

| key name | key value type | usage| notes |
|----------|----------------|------|-------|
| **`<field_name>`** | String | mandatory | This is the name for the question |
| **`<finder_return_value>`** | String | mandatory | This can be one of `"unique"` or `"multiple"` |

## Example 1: Simple field finders
Given the following `table` definition:

```
"tables": [
	{
		"name": "user",
		"fields": [
			{ "name": "id_user", "type": "bigint", "nullable": false, "primary": true },
			{ "name": "nm_user", "type": "varchar", "length": 100, "nullable": false },
			{ "name": "txt_address_email", "type": "varchar", "length": 256, "nullable": false, "unique": true },
			{ "name": "num_age", "type": "int", "nullable": false }
		]
	}
]
```

Adding the following `fieldFinders` JSON array of JSON Objects:

```
"fieldFinders": [
	{ "txt_address_email": "unique" },
	{ "num_age": "multiple" }
]
```

This will automatically generate two finders:

The first finder is `findByTxtAddressEmail` which will return a unique `User` object that has one parameter passed into the method, namely `txtAddressEmail`.

The second finder is `findByNumAge` which will return an `ArrayList<User>` that has all of the users with a specific age.

The alternate way to define the above finders is as follows (which would generate identical code):

```
"finders": [
	{
		"name": "findByTxtAddressEmail",
		"whereClause": "where txt_address_email = ?",
		"whereFields": [
			"txt_address_email"
		],
		"unique": true
	},
	{
		"name": "findByNumAge",
		"whereClause": "where num_age = ?",
		"whereFields": [
			"num_age"
		],
		"unique": false
	}
]
```

Which as you can see is a lot more work, more prone to error and un-necessary work for something that can be automatically generated.
