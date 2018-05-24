## DATA CONVERSION

1. Get most recent files from ffxiv-datamining repository (https://github.com/viion/ffxiv-datamining/tree/master/csv)
2. In CSV folder run `for file in *.csv; do sed -i.bak '1d' "$file"; done` to remove the first line.
3. convert to json from csv any or all files.
4. using NPM intalled csv2json
5. https://www.npmjs.com/package/csvtojson#command-line-usage
6. `for file in *.csv; do csvtojson "$file" > "$file".json; done`
7. DONE! Converted correctly all files!!
8. Make a process that makes sense to automate this a lot more.

