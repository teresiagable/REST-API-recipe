# REST API Assignment

REST API assignment at Lexicon's Fullstack developer course, java group 33.

## Seeding ingredients database with ingredients from livsmedelsverket

1. Change `almgru.restapi.seed-ingredients-database` from `false` to `true`.
2. Make sure the ingredients database is empty. To make sure it is you can temporarily change
   `spring.jpa.hibernate.ddl-auto` from `none` to `create`. Don't forget to change it back if you want to persist the
   data!
3. Start the application

## API

### Recipes

#### Get all recipes

`GET /api/recipes`

#### Get a single recipe by id

`GET /api/recipes/<id>`

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

`GET /api/recipes?ingredient=<ingredient name>`

The ingredient name must match exactly but case is ignored. `ingredient=eg` for example will not match 'Egg', but `ingredient=egg` will.

##### Examples

- `GET /api/recipes?ingredient=bacon`
- `GET /api/recipes?ingredient=vegetable+oil`

#### Create recipe

`POST /api/recipes`

Request body:
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

Any ingredients or categories that do not exist will be created.

### Ingredients

#### Get all ingredients

`GET /api/ingredients`

#### Get a single ingredient by id

`GET /api/ingredients/<id>`

#### Search ingredient by name

`GET /api/ingredients?query=<name query>`

#### Create ingredient

`POST /api/ingredients`

Request body: 
```
{
   "name": "<string>"
}
```
