Select
  id,
  name,
  case
  /* Elemental */
  when name like '%Lightning%' then 'white'
  when name like '%Fire%' then 'white'
  when name like '%Wind%' then 'white'
  when name like '%Water%' then 'white'
  when name like '%Ice%' then 'white'
  when name like '%Earth%' then 'white'
  /*Main Stat*/
  when name like '%Mind%' then 'skyblue'
  when name like '%Vitality%' then 'skyblue'
  when name like '%Strength%' then 'skyblue'
  when name like '%Intelligence%' then 'skyblue'
  when name like '%Dexterity%' then 'skyblue'
  /* DPS Combat */
  when name like '%Savage%' then 'red'
  when name like '%Heaven%' then 'red'
  /* DPS Combat */
  when name like '%Battle%' then 'yellow'
  when name like '%Piety%' then 'yellow'
  /* Speed Combat */
  when name like '%Quick%' then 'purple'
  /*Crafting*/
  when name like '%Craft%' then 'drkblue'
  /*Gathering */
  when name like '%Gatherer%' then 'green'
  else null end as color
from Materia group by name;