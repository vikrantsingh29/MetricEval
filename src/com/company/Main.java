package com.company;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import java.io.*;

public class Main
{

    public static void main(String[] args)
    {

        String inputFileName = "D:\\Version\\src\\com\\company\\data.rdf";
        Model model = ModelFactory.createDefaultModel();
        String queryString = "PREFIX dct:   <http://purl.org/dc/terms/> "
                + "PREFIX dcat:  <http://www.w3.org/ns/dcat#> "
                + "SELECT DISTINCT ?object "
                + "WHERE {"
                + " ?subject dcat:accessURL ?object .FILTER regex(str(?object), \"version\") ."
                + "}";

        Query my_query = QueryFactory.create(queryString);
        try
        {
            model.read(new FileInputStream(inputFileName), null, "TTL");
        }
        catch (FileNotFoundException e)
        {
                e.printStackTrace();
        }

        QueryExecution qexec = QueryExecutionFactory.create(my_query, model);
        ResultSet results = qexec.execSelect();

        int count = 0;
        while (results.hasNext())
        {
            QuerySolution soln = results.nextSolution();
            RDFNode predicate = soln.get("object");
            System.out.println(predicate);
            count++;
        }

        qexec.close();
        System.out.println("Total Metadata =" + count);
    }
}