# REST API Assignment

REST API assignment at Lexicon's Fullstack developer course, java group 33.

## API

### Recipes

#### Get all recipes

`GET /api/recipes`

#### Get a single recipe by id

`GET /api/recipes?id=<id>`

#### Get a single recipe by name 

`GET /api/recipes?name=<name>`

##### Examples

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

### Categories
