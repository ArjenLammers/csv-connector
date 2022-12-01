# Mendix CSV Connector (for import and export of CSV's)

## Description
This module offers a connector-style usage of CSV for import and exports.

Advantages of connector style:

- Model reflects CSV structure. 
- Microflow can be optimized for importing / exporting (e.g. by caching or type interpretation).
- Suitable for high volumes. (from 100K's to M's)

Microflow can be optimized for importing / exporting (e.g. by caching). - Suitable for high volumes - Streaming approach for higher performance and less memory consumption. Features: - Import CSV line by line - Write CSV line by line - Export to a CSV based on an OQL query - Export to a CSV based on a SQL query - Import a CSV having a simple structure using a SQL approach.

## Typical usage scenario
Imports or exports using the CSV file format.

Large volume export or import.

## Features and limitations
- Import CSV line by line 
- Write CSV line by line 
- Write a CSV based on an OQL query
- Write a CSV based on a SQL query

## Dependencies

OQL module >= 1.4


## Installation
- Download and install the OQL module >= 1.4 from the App Store.
- Download and install the CSV module from the App Store.

### Upgrade from 1.13
Remove the following files from `userlib`:
 - `opencsv-4.1.jar`

## Configuration
Create a (non-persistent) entity that reflects your CSV structure (columns).
The names of the attributes should be ordered alphabetical in respect to the CSV.

E.g. a CSV having the following contents:
`ID,Username,FirstName,LastName`
`1,jdoe,John,Doe`

Could have an entity with the following attributes to maintain an alphabetical order:
- C01_ID
- C02_Username
- C03_FirstName
- C04_LastName
 

## To import or export a CSV using the connector
- Use the action Export CSV or Import CSV
- Select a microflow which will be used to import or export the contents (open the file)
- Within this microflow, to import
  - Keep using Read next line 
  - If no object has been returned, the end of the file has been reached.
-  Within this microflow, to export
  - Keep using Write next line until all data is written
  - The CSV files will be closed when the microflow has finished.


## Known bugs

Mapping on entity is limited by the platform, which returns the declared entities in random order instead of modeled order, which requires the approach described in Configuration.

Use import and exports based on OQL or SQL for higher volume imports/exports using simple structures.