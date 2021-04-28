# Create a recipe

Create a new recipe by specifying name, instructions, ingredients and categories. Any ingredients or categories that do not already exist in the database will be created.

Multiple recipes can have the same name.

__URL:__ `/api/recipes`

__Method:__ `POST`

## Request

### Parameters

None

### Request Body

__Type__: JSON

__Schema__:
```
{
   "name": "<string>",
   "instructions": "<string>",
   "ingredients": [
      {
         "name": "<string>",
         "measurement": "<LITER|DECILITER|CENTILITER|KILOGRAM|GRAM|MILLIGRAM|PIECES>",
         "amount": <number>
      },
      ...
   ],
   "categories": [
      "<string>",
      ...
   ]
}
```

__Data types__:
- __name__: string
- __instructions__: string, max length 1500
- __ingredients__: JSON array
   + __name__: string
   + __measurement__: enum, one of `LITER`, `DECILITER`, `MILLILITER`, `KILOGRAM`, `GRAM`, `MILLIGRAM` or `PIECES`
   + __amount__: number
- __categories__: JSON array
   + __name__: string 

### Example

`POST /api/recipes`

```
{
    "name": "Pancakes",
    "instructions": "1. Whisk flour, egg and milk. 2. Let rest for 30 min. 3. Cook on frying pan 1 minute on each side.",
    "ingredients": [
        {
            "name": "plain flour",
            "measurement": "GRAM",
            "amount": 100
        },
        {
            "name": "large egg",
            "measurement": "PIECES",
            "amount": 2
        },
        {
            "name": "milk",
            "measurement": "DECILITER",
            "amount": 3
        }
    ],
    "categories": [
        "Simple"
    ]
}
```

## Response

Returns 200 OK on success or 400 BAD REQUEST if data doesn't match schema.

### 200 OK

Returns only status code with empty body on success. 

### 400 BAD REQUEST

Returns a JSON object describing the error.

__Type__: JSON

__Schema__:
```
{
   "timestamp": "<date and time of error>",
   "status": <status code>,
   "error": "<description of status code>",
   "message": "<error message>",
   "path": "<endpoint>"
}
```

__Example__:
```
{
    "timestamp": "2021-04-28 10:22:28",
    "status": 400,
    "error": "BAD_REQUEST",
    "message": "name - must not be null, name - must not be empty",
    "path": "uri=/api/recipes/"
}
```
