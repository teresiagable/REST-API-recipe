# REST API Assignment

REST API assignment at Lexicon's Fullstack developer course, java group 33.

## API

### Recipes

#### Get all recipes

`GET /api/recipes`

#### Get a single recipe by id

`GET /api/recipes?id=<id>`

#### Search recipes by name 

`GET /api/recipes?query=<name query>`

##### Examples

- `GET /api/recipes?query=pasta`
- `GET /api/recipes?name=spaghetti+bolognese`

#### Get all recipes that contains at least one of the specified categories

`GET /api/recipes?categories=<comma separated list of categories>`

##### Examples

- `GET /api/recipes?categories=pasta`
- `GET /api/recipes?categories=fast+food,indian,mexican`

#### Get all recipes that contains the specifed ingredient

`GET /api/recipes?ingredientName=<ingredient name>`

The ingredient name must match exactly but case is ignored. `ingredientName=eg` for example will not match 'Egg', but `ingredientName=egg` will.

##### Examples

- `GET /api/recipes?ingredientName=bacon`
- `GET /api/recipes?ingredientName=vegetable+oil`

### Ingredients

#### Get all ingredients

`GET /api/ingredients`

#### Get a single ingredient by id

`GET /api/ingredients?id=<id>`

#### Search ingredient by name

`GET /api/ingredients?query=<name query>`

### Categories
