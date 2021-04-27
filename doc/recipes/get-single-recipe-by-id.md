# Get single recipe by id

Get the recipe with the specified id.

__Example__: `GET /api/recipes/1`
  
__URL:__ `/api/recipes`

__Method:__ `GET`

## Parameters

### `id`

Integer path variable specifying the id of the recipe to get.
  
__Type__: path variable

__Data type__: integer


## Request Body

None

## Response

Returns 200 OK if recipe exists, otherwise 404 NOT FOUND.

### 200 OK

Returns the ingredient as a JSON object.

__Type__: `JSON`

__Schema__:
```
{
    "id": <id of ingredient>,
    "name": "<name of ingredient>"
}
```

__Data types__:
- _id_: integer
- _name_: string

#### Example

```
{
    "id": 666,
    "name": "Pasta kokt u. salt"
}
```
  
### 404 NOT FOUND

Returns 404 with empty body if no recipe exists with the specified id. 
