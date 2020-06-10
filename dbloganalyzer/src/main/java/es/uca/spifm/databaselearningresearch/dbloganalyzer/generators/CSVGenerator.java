package es.uca.spifm.databaselearningresearch.dbloganalyzer.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

@Component
public class CSVGenerator<T> {

	public void createCSVFile(List<T> data) throws IOException {

		List<String> headers = new ArrayList<String>();

		if (data != null && data.size() > 0) {

			T firstRecord = data.get(0);

			try {
				for (Method method : firstRecord.getClass().getMethods()) {

					if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {

						Class<?> realClass = method.getReturnType(); // this is a real class / implementation
						if (Collection.class.isAssignableFrom(realClass)) {
							int length;

							List invokation = (List) method.invoke(firstRecord);
							// length = Array.getLength(invokation);
							for (int i = 0; i < invokation.size(); i++) {
								headers.add(method.getName().replace("get", "") + (i + 1));
							}

						} else {
							headers.add(method.getName().replace("get", ""));

						}

					}
				}
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FileWriter out = new FileWriter(firstRecord.getClass().getSimpleName() + ".csv");
			try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.EXCEL.withHeader(headers.toArray(new String[0])))) {
				data.forEach((record) -> {
					try {

						List<Object> aux = new ArrayList<Object>();

						for (Method methodToInvoke : firstRecord.getClass().getMethods()) {
							if (methodToInvoke.getName().startsWith("get")
									&& !methodToInvoke.getName().equals("getClass")) {

								Class<?> realClass = methodToInvoke.getReturnType(); // this is a real class /
																						// implementation
								if (Collection.class.isAssignableFrom(realClass)) {

									List invokation = (List) methodToInvoke.invoke(record);
									// int length = Array.getLength(invokation);
									for (int i = 0; i < invokation.size(); i++) {
										aux.add(invokation.get(i));
									}

								} else {
									aux.add(methodToInvoke.invoke(record));
								}
							}
						}

						printer.printRecord(aux);
					} catch (IOException | SecurityException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}

		}

	}

}