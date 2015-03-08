# CONSTANTS

Constants are a way to tie a database table to constant Java objects.  This allows for strong references within the code base and also key relationships within the database.

The key `constants` is a JSON Array on the `table` JSON object, for example:

```
"tables": [
	{
		"fields": [
			...
		],
		"constants": [
			...
		]
	}
]
```

## Key Value Pairs

| key name | key value type | usage| notes |
|----------|----------------|------|-------|
| **name** | String | mandatory | This is the name for the constant |
| **values** | JSON Array | mandatory | These are the values that will be both referenced in the java code and generated in the database |

## Example 1: Constants
```
{
	"name": "media_type",
	"fields": [
		{ "name": "id_media_type", "type": "bigint", "nullable": false, "primary": true },
		{ "name": "nm_media_type", "type": "varchar", "length": "32", "nullable": false}
	],
	"constants": [
		{ "name": "BOOK", "values": [ 1, "book" ] },
		{ "name": "TV", "values": [ 2, "tv" ] },
		{ "name": "MOVIE", "values": [  3, "movie" ] }
	]
}
```

This will create a `MediaType` object with constant values that can be referenced in the code, and will generate the SQL to insert into the database.

The best illustration of this is the generated code and the SQL.

### The generated SQL

*Comments were removed from the generated SQL*


```
create table media_type (
	id_media_type bigint not null auto_increment,
	nm_media_type varchar(32) not null,
	primary key(id_media_type)
) engine=innodb default charset=UTF8;

insert into media_type values(1, 'book');
insert into media_type values(2, 'tv');
insert into media_type values(3, 'movie');
```

And from the Java code side:

```
public static final MediaType BOOK = new MediaType(new Long(1), "book");
public static final MediaType TV = new MediaType(new Long(2), "tv");
public static final MediaType MOVIE = new MediaType(new Long(3), "movie");

public static MediaType[] ALL =  {
	MediaType.BOOK, MediaType.TV, MediaType.MOVIE
};

public static HashMap<Long, MediaType> ALL_LOOKUP = new HashMap<Long, MediaType>();
static{
	ALL_LOOKUP.put(new Long(1), MediaType.BOOK);
	ALL_LOOKUP.put(new Long(2), MediaType.TV);
	ALL_LOOKUP.put(new Long(3), MediaType.MOVIE);
};

```

There are two ways to reference one of the constants within the code.

Either through a static reference, e.g.:

`MediaType.BOOK` which will return the `MediaType` object or through a lookup - which is used from a foreign keyed table: 

```
	public MediaType getMediaType() {
		return(MediaType.ALL_LOOKUP.get(this.idMediaType));
	}
```

rather than running a SQL query to lookup the value.

## Notes

  1. There are no setters on the fields for a constant table, only getters.
  1. You may still have `finders`, `fieldFinders`, `questions`, and `counters` on the generation
  1. However, you may not have anything that alters the underlying state of the database i.e. `deleters`, `updaters` and `inserters` as this must always be generated through the h2zero file.


