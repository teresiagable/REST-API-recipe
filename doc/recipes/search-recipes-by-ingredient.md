# Search recipes by ingredient

Get all recipes that contain the specified ingredient.

__URL:__ `/api/recipes`

__Method:__ `GET`

## Request

### Parameters

#### `ingredient`

Name of ingredient to use to search for recipes.

__Type__: Query parameter

__Data type__: string

### Request Body

None

### Example

`GET /api/recipes?ingredient=vegetable+oil`

## Response

Always returns `200 OK`, even if the result is an empty list.

### 200 OK

Returns a JSON array of ingredients, sorted by name.

__Type__: `JSON`

__Schema__:
```
[
    {
        "id": <id of first ingredient>,
        "name": "<name of first ingredient>"
    },
    {
        "id": <id of second ingredient>,
        "name": "<name of second ingredient>"
    },
    ...
]
```

__Data types__:
- _id_: integer
- _name_: string

#### Example 1

```
[
    {
        "id": 1090,
        "name": "Fiskgratäng m. potatismos räksås tillagad frysvara"
    },
    {
        "id": 1078,
        "name": "Fiskgratäng m. räkor u. potatismos"
    },
    {
        "id": 1071,
        "name": "Fiskgratäng u. potatismos hemlagad"
    }
]
```

#### Example 2

No results.

```
[]
```
