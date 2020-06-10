package es.uca.spifm.databaselearningresearch.samples.jsqlparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class Example {
	 public static void main(String[] args) throws Exception {
	 
		 Select stmt = (Select) CCJSqlParserUtil.parse("SELECT col1 AS a, col2 AS b, col3 AS c FROM table WHERE col1 = 10 AND col2 = 20 AND col3 = 30");
	        
		 Map<String, Expression> map = new HashMap<>();        
		 for (SelectItem selectItem : ((PlainSelect)stmt.getSelectBody()).getSelectItems()) {
		     selectItem.accept(new SelectItemVisitorAdapter() {
		         @Override
		         public void visit(SelectExpressionItem item) {
		             map.put(item.getAlias().getName(), item.getExpression());
		         }
		     });
		 }
		     
		 System.out.println("map " + map);
		 
		 
		 Statement statement = CCJSqlParserUtil.parse("SELECT * FROM tiendas WHERE tda_num IN (SELECT vnt_tda FROM ventas WHERE vnt_art IN (SELECT art_num FROM articulos WHERE art_col = 'azul'));");
		 Select selectStatement = (Select) statement;
		 TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		 List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		 tableList.forEach(t->System.out.println("t: "+t));
		 
		
	 }
	 
}
