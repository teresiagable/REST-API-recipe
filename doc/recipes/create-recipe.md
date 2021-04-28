# Create a recipe

Create a new recipe by specifying name, instructions, ingredients and categories. Any ingredients or categories that do not already exist in the database will be created. 

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
- _name_: string
- _instructions_: string, max length 1500
- _ingredients_: JSON array
   + _name_: string
   + _measurement_: enum, one of `LITER`, `DECILITER`, `MILLILITER`, `KILOGRAM`, `GRAM`, `MILLIGRAM` or `PIECES`
   + _amount_: number
- _categories_: JSON array
   + _name_: string 

### Example

`POST /api/recipes`

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

## Response

Returns 200 OK on success, 400 BAD REQUEST if data doesn't match schema, or 422 UNPROCESSABLE ENTITY on name conflict.

### 200 OK

Returns a String.

__Type__: `Plain text`

__Schema__:
```
<string>
```

__Data types__: None

#### Example

```
[
]
```

### 400 BAD REQUEST

Returns a JSON object describing the error.

__Type__: JSON

__Schema__:
```
{
}
```

### 422 UNPROCESSABLE ENTITY

Returns message describing the error.

__Type__: Plain text

__Schema__: None
