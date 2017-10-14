package com.stuzocub.util;

import java.util.List;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.util.PwdGenerator;

public class XmlUtil {

	private static final Log LOGGER = LogFactoryUtil.getLog(XmlUtil.class);

	/**
	 * Inserción del elemento dentro del nodo raiz
	 * 
	 * @param rootElement
	 * @param name
	 * @param type
	 * @param content
	 */
	public static void insertElement(Element rootElement, String name, String type,
	        String content) {
		Element element = rootElement.addElement("dynamic-element");
		String instanceId = generateInstanceId();
		element.addAttribute("instance-id", instanceId);
		element.addAttribute("name", name);
		element.addAttribute("type", type);

		Element elementContent = element.addElement("dynamic-content");
		elementContent.addCDATA(content);
	}


	/**
	 * Inserción de las categorias en el xml
	 * 
	 * @param rootElement
	 * @param name
	 * @param type
	 * @param categories
	 * @param categoriesSelected
	 */
	public static void insertCategories(Element rootElement, String name, String type,
	        List<AssetCategory> categories, long[] categoriesSelected) {
		Element element = rootElement.addElement("dynamic-element");
		String instanceId = generateInstanceId();
		element.addAttribute("instance-id", instanceId);
		element.addAttribute("name", name);
		element.addAttribute("type", type);

		for (AssetCategory category : categories) {
			String content = String.valueOf(Boolean.FALSE);
			if (category != null && contains(category.getCategoryId(), categoriesSelected)) {
				content = String.valueOf(Boolean.TRUE);
			}
			// TODO: Revisar el nombre de la categoria que se inserta en el XML!!!!
			// category.getName()???????????????????
			insertElement(element, category.getName(), "boolean", content);
		}
	}

	/**
	 * Generación del atributo instance-id
	 * 
	 * @return
	 */
	private static String generateInstanceId() {
		StringBuilder instanceId = new StringBuilder(8);

		String key = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

		for (int i = 0; i < 8; i++) {
			int pos = (int) Math.floor(Math.random() * key.length());

			instanceId.append(key.charAt(pos));
		}
		return instanceId.toString();
	}

	/**
	 * Comprobación de si el elemento pertenece a la lista
	 * 
	 * @param elem
	 * @param list
	 * @return
	 */
	private static boolean contains(long elem, long[] list) {
		boolean contains = false;
		for (int i = 0; i < list.length; i++) {
			if (elem == list[i]) {
				contains = true;
				break;
			}
		}
		return contains;
	}

}
