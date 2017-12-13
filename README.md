# fop-retrieve-table-markers-bug
demonstration of apache fop bug

when tables are nested, the fo:footer cannot properly retrieve markers and causes whole table to disappear.

this is most probably because org.apache.fop.fo.flow.RetrieveTableMarker#findAncestor 
flags ALL the parent fo:table-cell as HavingRetrieveTableMarker and as a result making them disappear

see [example pdf](result.pdf) which SHOULD display three identical tables. 
This is rendered from [markers.fo.xml](src/eu/jgwozdz/fop/markers.fo.xml)

it seems that valid fix would be to add something like this:

```
  if (temp.getNameId() == FO_TABLE && (ancestorID == FO_TABLE_HEADER || ancestorID == FO_TABLE_FOOTER)) {
      return -1;
  }
```
at apache's fop source /org/apache/fop/fo/flow/RetrieveTableMarker.java:104 
