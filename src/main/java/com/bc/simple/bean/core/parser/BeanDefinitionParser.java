package com.bc.simple.bean.core.parser;

import static com.bc.simple.bean.common.util.Constant.ATTRIBUTE;
import static com.bc.simple.bean.common.util.Constant.ATTR_ABSTRACT;
import static com.bc.simple.bean.common.util.Constant.ATTR_AUTOWIRE;
import static com.bc.simple.bean.common.util.Constant.ATTR_AUTOWIRE_CANDIDATE;
import static com.bc.simple.bean.common.util.Constant.ATTR_BEAN;
import static com.bc.simple.bean.common.util.Constant.ATTR_CLASS;
import static com.bc.simple.bean.common.util.Constant.ATTR_DEFAULT_AUTOWIRE;
import static com.bc.simple.bean.common.util.Constant.ATTR_DEFAULT_AUTOWIRE_CANDIDATES;
import static com.bc.simple.bean.common.util.Constant.ATTR_DEFAULT_DESTROY_METHOD;
import static com.bc.simple.bean.common.util.Constant.ATTR_DEFAULT_INIT_METHOD;
import static com.bc.simple.bean.common.util.Constant.ATTR_DEFAULT_LAZY_INIT;
import static com.bc.simple.bean.common.util.Constant.ATTR_DEFAULT_MERGE;
import static com.bc.simple.bean.common.util.Constant.ATTR_DEPENDS_ON;
import static com.bc.simple.bean.common.util.Constant.ATTR_DESTROY_METHOD;
import static com.bc.simple.bean.common.util.Constant.ATTR_FACTORY_BEAN;
import static com.bc.simple.bean.common.util.Constant.ATTR_FACTORY_BEAN_CLASS_NAME;
import static com.bc.simple.bean.common.util.Constant.ATTR_FACTORY_METHOD;
import static com.bc.simple.bean.common.util.Constant.ATTR_ID;
import static com.bc.simple.bean.common.util.Constant.ATTR_INDEX;
import static com.bc.simple.bean.common.util.Constant.ATTR_INIT_METHOD;
import static com.bc.simple.bean.common.util.Constant.ATTR_KEY;
import static com.bc.simple.bean.common.util.Constant.ATTR_KEY_REF;
import static com.bc.simple.bean.common.util.Constant.ATTR_KEY_TYPE;
import static com.bc.simple.bean.common.util.Constant.ATTR_LAZY_INIT;
import static com.bc.simple.bean.common.util.Constant.ATTR_MATCH;
import static com.bc.simple.bean.common.util.Constant.ATTR_MERGE;
import static com.bc.simple.bean.common.util.Constant.ATTR_NAME;
import static com.bc.simple.bean.common.util.Constant.ATTR_OVERRIDE_TYPE;
import static com.bc.simple.bean.common.util.Constant.ATTR_PARENT;
import static com.bc.simple.bean.common.util.Constant.ATTR_PRIMARY;
import static com.bc.simple.bean.common.util.Constant.ATTR_PROPERTY_TYPE;
import static com.bc.simple.bean.common.util.Constant.ATTR_REF;
import static com.bc.simple.bean.common.util.Constant.ATTR_REF_TYPE;
import static com.bc.simple.bean.common.util.Constant.ATTR_REPLACER;
import static com.bc.simple.bean.common.util.Constant.ATTR_ROOT;
import static com.bc.simple.bean.common.util.Constant.ATTR_SCOPE;
import static com.bc.simple.bean.common.util.Constant.ATTR_TYPE;
import static com.bc.simple.bean.common.util.Constant.ATTR_VALUE;
import static com.bc.simple.bean.common.util.Constant.ATTR_VALUE_REF;
import static com.bc.simple.bean.common.util.Constant.ATTR_VALUE_TYPE;
import static com.bc.simple.bean.common.util.Constant.AUTOWIRE_BY_NAME_VALUE;
import static com.bc.simple.bean.common.util.Constant.AUTOWIRE_BY_TYPE_VALUE;
import static com.bc.simple.bean.common.util.Constant.DEFAULT_VALUE;
import static com.bc.simple.bean.common.util.Constant.DOC_ARG_TYPE;
import static com.bc.simple.bean.common.util.Constant.DOC_ARRAY;
import static com.bc.simple.bean.common.util.Constant.DOC_BEAN;
import static com.bc.simple.bean.common.util.Constant.DOC_CONSTRUCTOR_ARG;
import static com.bc.simple.bean.common.util.Constant.DOC_DESCRIPTION;
import static com.bc.simple.bean.common.util.Constant.DOC_ENTRY;
import static com.bc.simple.bean.common.util.Constant.DOC_IDREF;
import static com.bc.simple.bean.common.util.Constant.DOC_KEY;
import static com.bc.simple.bean.common.util.Constant.DOC_LIST;
import static com.bc.simple.bean.common.util.Constant.DOC_LOOKUP_METHOD;
import static com.bc.simple.bean.common.util.Constant.DOC_MAP;
import static com.bc.simple.bean.common.util.Constant.DOC_META;
import static com.bc.simple.bean.common.util.Constant.DOC_NULL;
import static com.bc.simple.bean.common.util.Constant.DOC_PROP;
import static com.bc.simple.bean.common.util.Constant.DOC_PROPERTY;
import static com.bc.simple.bean.common.util.Constant.DOC_PROPS;
import static com.bc.simple.bean.common.util.Constant.DOC_REF;
import static com.bc.simple.bean.common.util.Constant.DOC_REPLACED_METHOD;
import static com.bc.simple.bean.common.util.Constant.DOC_SET;
import static com.bc.simple.bean.common.util.Constant.DOC_VALUE;
import static com.bc.simple.bean.common.util.Constant.FALSE_VALUE;
import static com.bc.simple.bean.common.util.Constant.MULTI_VALUE_ATTRIBUTE_DELIMITERS;
import static com.bc.simple.bean.common.util.Constant.OVERRIDE_TYPE_INIT_VALUE;
import static com.bc.simple.bean.common.util.Constant.OVERRIDE_TYPE_REPLACE_VALUE;
import static com.bc.simple.bean.common.util.Constant.TRUE_VALUE;
import static com.bc.simple.bean.common.util.Constant.TYPE_REF_VALUE;
import static com.bc.simple.bean.common.util.Constant.TYPE_STRING_VALUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bc.simple.bean.BeanDefinition;
import com.bc.simple.bean.BeanFactory;
import com.bc.simple.bean.common.config.ConfigLoader.Node;
import com.bc.simple.bean.common.util.BeanUtils;
import com.bc.simple.bean.common.util.Constant;
import com.bc.simple.bean.common.util.DomUtils;
import com.bc.simple.bean.common.util.ObjectUtils;
import com.bc.simple.bean.common.util.StringUtils;
import com.bc.simple.bean.core.handler.HandlerProxy;
import com.bc.simple.bean.core.support.CurrencyException;
import com.bc.simple.bean.core.support.AnnotationMetaData.SimpleAnnotationVisitor;

/**
 * Stateful delegate class used to parse XML bean definitions. Intended for use
 * by both the main parser and any extension {@link BeanDefinitionParser
 * BeanDefinitionParsers} or {@link BeanDefinitionDecorator
 * BeanDefinitionDecorators}.
 *
 * @since 2.0
 * @see ParserContext
 * @see DefaultBeanDefinitionDocumentReader
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BeanDefinitionParser {

	private Log log = LogFactory.getLog(SimpleAnnotationVisitor.class);

	private final BeanFactory beanFactory;

	private final Node root;

	/**
	 * Stores all used bean names so we can enforce uniqueness on a per beans-Node
	 * basis. Duplicate bean ids/names may not exist within the same level of beans
	 * Node nesting, but may be duplicated across levels.
	 */
	private final Set<String> usedNames = new HashSet<>();

	private final HandlerProxy handler;

	/**
	 * Create a new BeanDefinitionParserDelegate associated with the supplied
	 * {@link XmlReaderContext}.
	 */
	public BeanDefinitionParser(BeanFactory beanFactory, Node root) {
		this.beanFactory = beanFactory;
		this.handler = new HandlerProxy(beanFactory);
		this.root = root;
	}

	/**
	 * Parses the supplied {@code <bean>} Node. May return {@code null} if there
	 * were errors during parse. Errors are reported to the
	 * {@link org.Factory.beans.factory.parsing.ProblemReporter}.
	 */

	public BeanDefinition parseBeanDefinitionNode(Node ele) {
		return parseBeanDefinitionNode(ele, null);
	}

	/**
	 * Parses the supplied {@code <bean>} Node. May return {@code null} if there
	 * were errors during parse. Errors are reported to the
	 * {@link org.Factory.beans.factory.parsing.ProblemReporter}.
	 */

	public BeanDefinition parseBeanDefinitionNode(Node ele, BeanDefinition containingBean) {
		String id = ele.attrString(ATTR_ID);
		String nameAttr = ele.attrString(ATTR_NAME);

		List<String> aliases = new ArrayList<>();
		if (StringUtils.hasLength(nameAttr)) {
			String[] nameArr = StringUtils.tokenizeToStringArray(nameAttr, MULTI_VALUE_ATTRIBUTE_DELIMITERS, true,
					true);
			aliases.addAll(Arrays.asList(nameArr));
		}

		String beanName = id;
		if (!StringUtils.hasText(beanName) && !aliases.isEmpty()) {
			beanName = aliases.remove(0);
		}
		checkNameUniqueness(beanName, aliases, ele);
		BeanDefinition beanDefinition = parseBeanDefinitionNode(ele, beanName, containingBean);
		if (beanDefinition != null) {
			if (!StringUtils.hasText(beanName)) {
				try {
					if (containingBean != null) {
						beanName = BeanUtils.generateBeanName(beanDefinition, beanFactory, true);
					} else if (beanDefinition.isAnnotated()) {
						beanName = BeanUtils.generateAnnotatedBeanName(beanDefinition, beanFactory);
					} else {
						String beanClassName = beanDefinition.getBeanClassName();
						beanName = BeanUtils.generateBeanName(beanDefinition, beanFactory, false);
						if (!beanFactory.isBeanNameInUse(beanClassName)) {
							aliases.add(beanClassName);
						}
					}

				} catch (Exception ex) {
					// ignore
					return null;
				}
			}
			String[] aliasesArray = aliases.toArray(new String[aliases.size()]);
			beanDefinition.setAliases(aliasesArray);
			beanDefinition.setBeanName(beanName);
			return beanDefinition;
		}
		return null;
	}

	/**
	 * Validate that the specified bean name and aliases have not been used already
	 * within the current level of beans Node nesting.
	 */
	protected void checkNameUniqueness(String beanName, List<String> aliases, Node beanNode) {
		String foundName = null;

		if (StringUtils.hasText(beanName) && this.usedNames.contains(beanName)) {
			foundName = beanName;
		}
		if (foundName == null) {
			foundName = ObjectUtils.findFirstMatch(this.usedNames, aliases);
		}
		if (foundName != null) {
			throw new CurrencyException("Bean name '" + foundName + "' is already used in this <beans> Node", beanNode);
		}
		this.usedNames.add(beanName);
		this.usedNames.addAll(aliases);
	}

	/**
	 * Parse the bean definition itself, without regard to name or aliases. May
	 * return {@code null} if problems occurred during the parsing of the bean
	 * definition.
	 */

	public BeanDefinition parseBeanDefinitionNode(Node ele, String beanName, BeanDefinition containingBean) {
		String className = null;
		if (ele.hasAttr(ATTR_CLASS)) {
			className = ele.attrString(ATTR_CLASS).trim();
		}
		try {
			BeanDefinition bd = createBeanDefinition(className);
			parseBeanDefinitionAttributes(ele, beanName, containingBean, bd);
			bd.setDescription(DomUtils.getChildElementValueByTagName(ele, DOC_DESCRIPTION));
			parseMetaNodes(ele, bd);
			parseLookupOverrideSubNodes(ele, bd.getMethodOverrides());
			parseReplacedMethodSubNodes(ele, bd.getMethodOverrides());
			parseConstructorArgNodes(ele, bd);
			parsePropertyNodes(ele, bd);
			bd.setRoot(this.root);
			return bd;
		} catch (ClassNotFoundException ex) {
			throw new CurrencyException("Bean class [" + className + "] not found", ex);
		} catch (NoClassDefFoundError err) {
			throw new CurrencyException("Class that bean class [" + className + "] depends on not found", err);
		} catch (Throwable ex) {
			throw new CurrencyException("Unexpected failure during bean definition parsing", ex);
		}
	}

	/**
	 * Apply the attributes of the given bean Node to the given bean * definition.
	 * 
	 * @param ele            bean declaration Node
	 * @param beanName       bean name
	 * @param containingBean containing bean definition
	 * @return a bean definition initialized according to the bean Node attributes
	 */
	public BeanDefinition parseBeanDefinitionAttributes(Node ele, String beanName, BeanDefinition containingBean,
			BeanDefinition bd) {

		if (ele.hasAttr(ATTR_SCOPE)) {
			bd.setScope(ele.attrString(ATTR_SCOPE));
		} else if (containingBean != null) {
			// Take default from containing bean in case of an inner bean definition.
			bd.setScope(containingBean.getScope());
		}

		if (ele.hasAttr(ATTR_ABSTRACT)) {
			bd.setAbstract(TRUE_VALUE.equals(ele.attrString(ATTR_ABSTRACT)));
		}
		String tmp;
		String lazyInit = ele.attrString(ATTR_LAZY_INIT);
		if (StringUtils.isEmpty(lazyInit)) {
			if (StringUtils.isNotEmpty(tmp = this.root.attrString(ATTR_DEFAULT_LAZY_INIT))) {
				lazyInit = tmp;
			} else {
				lazyInit = FALSE_VALUE;
			}
		}
		bd.setLazyInit(TRUE_VALUE.equals(lazyInit));

		String autowire = ele.attrString(ATTR_AUTOWIRE);
		bd.setAutowireMode(getAutowireMode(autowire));

		if (ele.hasAttr(ATTR_DEPENDS_ON)) {
			String dependsOn = ele.attrString(ATTR_DEPENDS_ON);
			bd.setDependsOn(StringUtils.splitByStr(dependsOn, MULTI_VALUE_ATTRIBUTE_DELIMITERS));
		}

		String autowireCandidate = ele.attrString(ATTR_AUTOWIRE_CANDIDATE);
		if (StringUtils.isEmpty(autowireCandidate)) {
			String candidatePattern = this.root.attrString(ATTR_DEFAULT_AUTOWIRE_CANDIDATES);
			if (candidatePattern != null) {
				String[] patterns = StringUtils.splitByStr(candidatePattern, StringUtils.COMMA);
				bd.setAutowireCandidate(StringUtils.match(patterns, beanName));
			} else {
				bd.setAutowireCandidate(true);
			}
		} else {
			bd.setAutowireCandidate(TRUE_VALUE.equals(autowireCandidate));
		}

		if (ele.hasAttr(ATTR_PRIMARY)) {
			bd.setPrimary(TRUE_VALUE.equals(ele.attrString(ATTR_PRIMARY)));
		}

		if (ele.hasAttr(ATTR_INIT_METHOD)) {
			String initMethodName = ele.attrString(ATTR_INIT_METHOD);
			bd.setInitMethodName(initMethodName);
		} else if ((tmp = root.attrString(ATTR_DEFAULT_INIT_METHOD)) != null) {
			bd.setInitMethodName(tmp);
			bd.setEnforceInitMethod(false);
		}

		if (ele.hasAttr(ATTR_DESTROY_METHOD)) {
			String destroyMethodName = ele.attrString(ATTR_DESTROY_METHOD);
			bd.setDestroyMethodName(destroyMethodName);
		} else if ((tmp = root.attrString(ATTR_DEFAULT_DESTROY_METHOD)) != null) {
			bd.setDestroyMethodName(tmp);
			bd.setEnforceDestroyMethod(false);
		}

		if (ele.hasAttr(ATTR_FACTORY_METHOD)) {
			bd.setFactoryMethodName(ele.attrString(ATTR_FACTORY_METHOD));
		}

		if (ele.hasAttr(ATTR_FACTORY_BEAN)) {
			bd.setFactoryBeanName(ele.attrString(ATTR_FACTORY_BEAN));
		}

		if (ele.hasAttr(ATTR_FACTORY_BEAN_CLASS_NAME)) {
			bd.setFactoryBeanClassName(ele.attrString(ATTR_FACTORY_BEAN_CLASS_NAME));
		}

		return bd;
	}

	/**
	 * Create a bean definition for the given class name and parent name.
	 * 
	 * @param className  the name of the bean class
	 * @param parentName the name of the bean's parent bean
	 * @return the newly created bean definition
	 * @throws ClassNotFoundException if bean class resolution was attempted but
	 *                                failed
	 */
	protected BeanDefinition createBeanDefinition(String className) throws ClassNotFoundException {
		return BeanUtils.createBeanDefinition(className, beanFactory.getBeanClassLoader());
	}

	public void parseMetaNodes(Node ele, BeanDefinition bd) {
		if (bd == null) {
			throw new CurrencyException("BeanDefinition can not be null");
		}
		List<Node> nl = ele.getChilds();
		for (Node node : nl) {
			if (nodeNameEquals(node, DOC_META)) {
				Node metaNode = node;
				String key = metaNode.attrString(ATTR_KEY);
				String value = metaNode.attrString(ATTR_VALUE);
				bd.setAttribute(key, value);
			}
		}
	}

	public void parseMetaNodes(Node ele, Object target) {
		if (target == null) {
			throw new CurrencyException("target obj can not be null");
		}
		if (target instanceof Map) {
			Map map = (Map) target;
			List<Node> nl = ele.getChilds();
			for (Node node : nl) {
				if (nodeNameEquals(node, DOC_META)) {
					Node metaNode = node;
					String key = metaNode.attrString(ATTR_KEY);
					String value = metaNode.attrString(ATTR_VALUE);
					Map attribute = new HashMap<>();
					attribute.put(ATTR_KEY, key);
					attribute.put(ATTR_VALUE, value);
					map.put(ATTRIBUTE, attribute);
				}
			}
		}

	}

	public int getAutowireMode(String attValue) {
		String att = attValue;
		if (StringUtils.isEmpty(att)) {
			att = this.root.attrString(ATTR_DEFAULT_AUTOWIRE);
		}
		int autowire = BeanDefinition.AUTOWIRE_NO;
		if (AUTOWIRE_BY_NAME_VALUE.equals(att)) {
			autowire = BeanDefinition.AUTOWIRE_BY_NAME;
		} else if (AUTOWIRE_BY_TYPE_VALUE.equals(att)) {
			autowire = BeanDefinition.AUTOWIRE_BY_TYPE;
		}
		return autowire;
	}

	/**
	 * Parse constructor-arg sub-Nodes of the given bean Node.
	 */
	public void parseConstructorArgNodes(Node beanEle, BeanDefinition bd) {
		List<Node> nl = beanEle.getChilds();
		for (Node node : nl) {
			if (nodeNameEquals(node, DOC_CONSTRUCTOR_ARG)) {
				parseConstructorArgNode(node, bd);
			}
		}
	}

	/**
	 * Parse property sub-Nodes of the given bean Node.
	 */
	public void parsePropertyNodes(Node beanEle, BeanDefinition bd) {
		List<Node> nl = beanEle.getChilds();
		for (Node node : nl) {
			if (nodeNameEquals(node, DOC_PROPERTY)) {
				parsePropertyNode(node, bd);
			}
		}
	}

	/**
	 * Parse lookup-override sub-Nodes of the given bean Node.
	 */
	public void parseLookupOverrideSubNodes(Node beanEle, Set<Map<String, Object>> overrides) {
		List<Node> nl = beanEle.getChilds();
		for (Node node : nl) {
			if (nodeNameEquals(node, DOC_LOOKUP_METHOD)) {
				Node ele = node;
				String methodName = ele.attrString(ATTR_NAME);
				String beanRef = ele.attrString(ATTR_BEAN);
				Map override = new HashMap<String, Object>(1);
				override.put(ATTR_NAME, methodName);
				override.put(ATTR_BEAN, beanRef);
				override.put(ATTR_ROOT, this.root);
				override.put(ATTR_OVERRIDE_TYPE, OVERRIDE_TYPE_INIT_VALUE);
				overrides.add(override);
			}
		}
	}

	/**
	 * Parse replaced-method sub-Nodes of the given bean Node.
	 */
	public void parseReplacedMethodSubNodes(Node beanEle, Set<Map<String, Object>> overrides) {
		List<Node> nl = beanEle.getChilds();
		for (Node node : nl) {
			if (nodeNameEquals(node, DOC_REPLACED_METHOD)) {
				Node replacedMethodEle = node;
				String name = replacedMethodEle.attrString(ATTR_NAME);
				List<Node> argTypes = replacedMethodEle.getChilds();
				List<Class<?>> types = new ArrayList<>();
				for (int j = 0; j < argTypes.size(); j++) {
					Node supNode = argTypes.get(j);
					Node argType = supNode;
					Class type = BeanUtils.forName(argType.attrString(ATTR_MATCH), null);
					types.add(type);
				}
				String callback = replacedMethodEle.attrString(ATTR_REPLACER);
				Map override = new HashMap<String, Object>(1);
				override.put(ATTR_NAME, name);
				override.put(ATTR_REPLACER, callback);
				override.put(ATTR_ROOT, this.root);
				override.put(ATTR_OVERRIDE_TYPE, OVERRIDE_TYPE_REPLACE_VALUE);
				override.put(DOC_ARG_TYPE, types);
				// Look for arg-type match Nodes.
				overrides.add(override);
			}
		}
	}

	/**
	 * Parse a constructor-arg Node.
	 */
	public void parseConstructorArgNode(Node ele, BeanDefinition bd) {
		String indexAttr = ele.attrString(ATTR_INDEX);
		String typeAttr = ele.attrString(ATTR_TYPE);
		String nameAttr = ele.attrString(ATTR_NAME);
		if (StringUtils.hasLength(indexAttr)) {
			try {
				int index = Integer.parseInt(indexAttr);
				if (index < 0) {
					log.info("'index' cannot be lower than 0" + ele);
				} else {
					Object value = parsePropertyValue(ele, bd, null);
					Map map = new HashMap<>();
					map.put(ATTR_VALUE, value);
					if (StringUtils.hasLength(typeAttr)) {
						map.put(ATTR_TYPE, typeAttr);
					}
					if (StringUtils.hasLength(nameAttr)) {
						map.put(ATTR_NAME, nameAttr);
					}
					map.put(ATTR_ROOT, this.root);
					if (bd.getConstructorArgumentValues().containsKey(index)) {
						log.info("Ambiguous constructor-arg entries for index " + index + ele);
					} else {
						bd.getConstructorArgumentValues().put(index, map);
					}
				}
			} catch (NumberFormatException ex) {
				log.info("Attribute 'index' of tag 'constructor-arg' must be an integer" + ele);
			}
		} else {
			Object value = parsePropertyValue(ele, bd, null);
			Map<String, Object> map = new HashMap<>();
			map.put(ATTR_VALUE, value);
			if (StringUtils.hasLength(typeAttr)) {
				map.put(ATTR_TYPE, typeAttr);
			}
			if (StringUtils.hasLength(nameAttr)) {
				map.put(ATTR_NAME, nameAttr);
			}
			map.put(ATTR_ROOT, this.root);
			int index = ObjectUtils.generateId(bd.getConstructorArgumentValues());
			bd.getConstructorArgumentValues().put(index, map);
		}
	}

	/**
	 * Parse a property Node.
	 */
	public void parsePropertyNode(Node ele, BeanDefinition bd) {
		String propertyName = ele.attrString(ATTR_NAME);
		if (!StringUtils.hasLength(propertyName)) {
			log.info("Tag 'property' must have a 'name' attribute " + ele);
			return;
		}
		Object val = parsePropertyValue(ele, bd, propertyName);
		Map prop = new HashMap<>();
		prop.put(ATTR_NAME, propertyName);
		prop.put(ATTR_VALUE, val);
		parseMetaNodes(ele, prop);
		prop.put(ATTR_ROOT, this.root);
		bd.getPropertyValues().add(prop);
	}

	/**
	 * Get the value of a property Node. May be a list etc. Also used for
	 * constructor arguments, "propertyName" being null in this case.
	 */

	public Object parsePropertyValue(Node ele, BeanDefinition bd, String propertyName) {
		String NodeName = (propertyName != null ? "<property> Node for property '" + propertyName + "'"
				: "<constructor-arg> Node");

		// Should only have one child Node: ref, value, list, etc.
		List<Node> nl = ele.getChilds();
		Node subNode = null;
		for (Node node : nl) {
			if (!nodeNameEquals(node, DOC_DESCRIPTION) && !nodeNameEquals(node, DOC_META)) {
				// Child Node is what we're looking for.
				if (subNode != null) {
					log.info(NodeName + " must not contain more than one sub-Node " + ele);
				} else {
					subNode = node;
				}
			}
		}

		boolean hasRefAttribute = ele.hasAttr(ATTR_REF);
		boolean hasValueAttribute = ele.hasAttr(ATTR_VALUE);
		if ((hasRefAttribute && hasValueAttribute) || ((hasRefAttribute || hasValueAttribute) && subNode != null)) {
			log.info(
					NodeName + " is only allowed to contain either 'ref' attribute OR 'value' attribute OR sub-Node "
							+
					ele);
		}

		if (hasRefAttribute) {
			String refName = ele.attrString(ATTR_REF);
			String refTypeName = ele.attrString(ATTR_REF_TYPE);
			if (!StringUtils.hasText(refName)) {
				log.info(NodeName + " contains empty 'ref' attribute " + ele);
			}
			Map ref = new HashMap<String, Object>(3);
			ref.put(ATTR_PROPERTY_TYPE, TYPE_REF_VALUE);
			ref.put(ATTR_REF, refName);
			ref.put(ATTR_REF_TYPE, refTypeName);
			ref.put(ATTR_ROOT, this.root);
			return ref;
		} else if (hasValueAttribute) {
			String value = ele.attrString(ATTR_VALUE);
			Map prop = new HashMap<String, Object>(3);
			prop.put(ATTR_PROPERTY_TYPE, TYPE_STRING_VALUE);
			prop.put(ATTR_VALUE, value);
			prop.put(ATTR_ROOT, this.root);
			return prop;
		} else if (subNode != null) {
			return parsePropertySubNode(subNode, bd);
		} else {
			// Neither child Node nor "ref" or "value" attribute found.
			log.info(NodeName + " must specify a ref or value " + ele);
			return null;
		}
	}

	public Object parsePropertySubNode(Node ele, BeanDefinition bd) {
		return parsePropertySubNode(ele, bd, null);
	}

	/**
	 * Parse a value, ref or collection sub-Node of a property or constructor-arg
	 * Node.
	 * 
	 * @param ele              subNode of property Node; we don't know which yet
	 * @param defaultValueType the default type (class name) for any {@code <value>}
	 *                         tag that might be created
	 */

	public Object parsePropertySubNode(Node ele, BeanDefinition bd, String defaultValueType) {
		if (nodeNameEquals(ele, DOC_BEAN)) {
			BeanDefinition nestedBd = parseBeanDefinitionNode(ele, bd);
			if (nestedBd != null) {
				nestedBd = decorateBeanDefinitionIfRequired(ele, nestedBd);
			}
			return nestedBd;
		} else if (nodeNameEquals(ele, DOC_REF)) {
			// A generic reference to any name of any bean.
			String refName = ele.attrString(ATTR_BEAN);
			if (!StringUtils.hasLength(refName)) {
				// A reference to the id of another bean in a parent context.
				refName = ele.attrString(ATTR_PARENT);
				if (!StringUtils.hasLength(refName)) {
					log.info("'bean' or 'parent' is required for <ref> Node " + ele);
					return null;
				}
			}
			if (!StringUtils.hasText(refName)) {
				log.info("<ref> Node contains empty target attribute " + ele);
				return null;
			}
			Map ref = new HashMap<>(3);
			ref.put(ATTR_REF, refName);
			ref.put(ATTR_ROOT, this.root);
			return ref;
		} else if (nodeNameEquals(ele, DOC_IDREF)) {
			return parseIdRefNode(ele);
		} else if (nodeNameEquals(ele, DOC_VALUE)) {
			return parseValueNode(ele, defaultValueType);
		} else if (nodeNameEquals(ele, DOC_NULL)) {
			// It's a distinguished null value. Let's wrap it in a TypedStringValue
			// object in order to preserve the source location.
			return null;
		} else if (nodeNameEquals(ele, DOC_ARRAY)) {
			return parseArrayNode(ele, bd);
		} else if (nodeNameEquals(ele, DOC_LIST)) {
			return parseListNode(ele, bd);
		} else if (nodeNameEquals(ele, DOC_SET)) {
			return parseSetNode(ele, bd);
		} else if (nodeNameEquals(ele, DOC_MAP)) {
			return parseMapNode(ele, bd);
		} else if (nodeNameEquals(ele, DOC_PROPS)) {
			return parsePropsNode(ele);
		} else {
			return parseNestedCustomNode(ele, bd);
		}
	}

	/**
	 * Return a typed String value Object for the given 'idref' Node.
	 */

	public Object parseIdRefNode(Node ele) {
		// A generic reference to any name of any bean.
		String refName = ele.attrString(ATTR_BEAN);
		if (!StringUtils.hasLength(refName)) {
			log.info("'bean' is required for <idref> Node " + ele);
			return null;
		}
		if (!StringUtils.hasText(refName)) {
			log.info("<idref> Node contains empty target attribute " + ele);
			return null;
		}
		Map ref = new HashMap<>(2);
		ref.put(ATTR_NAME, refName);
		ref.put(ATTR_ROOT, this.root);
		return ref;
	}

	/**
	 * Return a typed String value Object for the given value Node.
	 */
	public Object parseValueNode(Node ele, String defaultTypeName) {
		// It's a literal value.
		String value = DomUtils.getTextValue(ele);
		String specifiedTypeName = ele.attrString(ATTR_TYPE);
		String typeName = specifiedTypeName;
		if (!StringUtils.hasText(typeName)) {
			typeName = defaultTypeName;
		}
		Map map = new HashMap<>(2);
		map.put(typeName, value);
		map.put(ATTR_ROOT, this.root);
		return map;
	}

	/**
	 * Parse an array Node.
	 */
	public List<Object> parseArrayNode(Node arrayEle, BeanDefinition bd) {
		String NodeType = arrayEle.attrString(ATTR_VALUE_TYPE);
		List<Node> nl = arrayEle.getChilds();
		List target = new ArrayList<>(nl.size());
		Map attribute = new HashMap<>(3);
		attribute.put(ATTR_ROOT, this.root);
		attribute.put(ATTR_TYPE, NodeType);
		attribute.put(ATTR_MERGE, parseMergeAttribute(arrayEle));
		target.add(attribute);
		parseCollectionNodes(nl, target, bd, NodeType);
		return target;
	}

	/**
	 * Parse a list Node.
	 */
	public List<Object> parseListNode(Node collectionEle, BeanDefinition bd) {
		return parseArrayNode(collectionEle, bd);
	}

	/**
	 * Parse a set Node.
	 */
	public Set<Object> parseSetNode(Node collectionEle, BeanDefinition bd) {
		List<Object> list = parseListNode(collectionEle, bd);
		Set set = new HashSet<>();
		set.addAll(list);
		return set;
	}

	protected void parseCollectionNodes(List<Node> nodes, Collection<Object> target, BeanDefinition bd,
			String defaultNodeType) {

		for (Node node : nodes) {
			if (!nodeNameEquals(node, DOC_DESCRIPTION)) {
				target.add(parsePropertySubNode(node, bd, defaultNodeType));
			}
		}
	}

	/**
	 * Parse a map Node.
	 */
	public Map<Object, Object> parseMapNode(Node mapEle, BeanDefinition bd) {
		String defaultKeyType = mapEle.attrString(ATTR_KEY_TYPE);
		String defaultValueType = mapEle.attrString(ATTR_VALUE_TYPE);
		List<Node> entryEles = DomUtils.getChildElementsByTagName(mapEle, DOC_ENTRY);
		Map<Object, Object> map = new HashMap<>(entryEles.size());
		map.put(ATTR_ROOT, this.root);
		map.put(ATTR_KEY_TYPE, defaultKeyType);
		map.put(ATTR_VALUE_TYPE, defaultValueType);
		map.put(ATTR_MERGE, parseMergeAttribute(mapEle));

		for (Node entryEle : entryEles) {
			// Should only have one value child Node: ref, value, list, etc.
			// Optionally, there might be a key child Node.
			List<Node> entrySubNodes = entryEle.getChilds();
			Node keyEle = null;
			Node valueEle = null;
			for (Node node : entrySubNodes) {
				Node candidateEle = node;
				if (nodeNameEquals(candidateEle, DOC_KEY)) {
					if (keyEle != null) {
						log.info("<entry> Node is only allowed to contain one <key> sub-Node " + entryEle);
					} else {
						keyEle = candidateEle;
					}
				} else {
					// Child Node is what we're looking for.
					if (nodeNameEquals(candidateEle, DOC_DESCRIPTION)) {
						// the Node is a <description> -> ignore it
					} else if (valueEle != null) {
						log.info("<entry> Node must not contain more than one value sub-Node " + entryEle);
					} else {
						valueEle = candidateEle;
					}
				}
			}

			// Extract key from attribute or sub-Node.
			Object key = null;
			boolean hasKeyAttribute = entryEle.hasAttr(ATTR_KEY);
			boolean hasKeyRefAttribute = entryEle.hasAttr(ATTR_KEY_REF);
			if ((hasKeyAttribute && hasKeyRefAttribute) || (hasKeyAttribute || hasKeyRefAttribute) && keyEle != null) {
				log.info("<entry> Node is only allowed to contain either "
						+ "a 'key' attribute OR a 'key-ref' attribute OR a <key> sub-Node " + entryEle);
			}
			if (hasKeyAttribute) {
				key = buildTypedStringValueForMap(entryEle.attrString(ATTR_KEY), defaultKeyType, entryEle);
			} else if (hasKeyRefAttribute) {
				String refName = entryEle.attrString(ATTR_KEY_REF);
				if (!StringUtils.hasText(refName)) {
					log.info("<entry> Node contains empty 'key-ref' attribute " + entryEle);
				}
				Map ref = new HashMap<>();
				map.put(ATTR_NAME, refName);
				map.put(ATTR_ROOT, this.root);
				key = ref;
			} else if (keyEle != null) {
				key = parseKeyNode(keyEle, bd, defaultKeyType);
			} else {
				log.info("<entry> Node must specify a key " + entryEle);
			}

			// Extract value from attribute or sub-Node.
			Object value = null;
			boolean hasValueAttribute = entryEle.hasAttr(ATTR_VALUE);
			boolean hasValueRefAttribute = entryEle.hasAttr(ATTR_VALUE_REF);
			boolean hasValueTypeAttribute = entryEle.hasAttr(ATTR_VALUE_TYPE);
			if ((hasValueAttribute && hasValueRefAttribute)
					|| (hasValueAttribute || hasValueRefAttribute) && valueEle != null) {
				log.info("<entry> Node is only allowed to contain either "
						+ "'value' attribute OR 'value-ref' attribute OR <value> sub-Node " + entryEle);
			}
			if ((hasValueTypeAttribute && hasValueRefAttribute) || (hasValueTypeAttribute && !hasValueAttribute)
					|| (hasValueTypeAttribute && valueEle != null)) {
				log.info("<entry> Node is only allowed to contain a 'value-type' "
						+ "attribute when it has a 'value' attribute " + entryEle);
			}
			if (hasValueAttribute) {
				String valueType = entryEle.attrString(ATTR_VALUE_TYPE);
				if (!StringUtils.hasText(valueType)) {
					valueType = defaultValueType;
				}
				value = buildTypedStringValueForMap(entryEle.attrString(ATTR_VALUE), valueType, entryEle);
			} else if (hasValueRefAttribute) {
				String refName = entryEle.attrString(ATTR_VALUE_REF);
				if (!StringUtils.hasText(refName)) {
					log.info("<entry> Node contains empty 'value-ref' attribute " + entryEle);
				}
				Map ref = new HashMap<>();
				map.put(ATTR_NAME, refName);
				map.put(ATTR_ROOT, this.root);
				value = ref;
			} else if (valueEle != null) {
				value = parsePropertySubNode(valueEle, bd, defaultValueType);
			} else {
				log.info("<entry> Node must specify a value " + entryEle);
			}

			// Add final key and value to the Map.
			map.put(key, value);
		}

		return map;
	}

	/**
	 * Build a typed String value Object for the given raw value.
	 * 
	 * @see org.Factory.beans.factory.config.TypedStringValue
	 */
	protected final Object buildTypedStringValueForMap(String value, String defaultTypeName, Node entryEle) {
		Map typedValue = new HashMap<>(2);
		typedValue.put(ATTR_TYPE, defaultTypeName);
		typedValue.put(ATTR_VALUE, value);
		return typedValue;
	}

	/**
	 * Parse a key sub-Node of a map Node.
	 */

	protected Object parseKeyNode(Node keyEle, BeanDefinition bd, String defaultKeyTypeName) {
		List<Node> nl = keyEle.getChilds();
		Node subNode = null;
		for (Node node : nl) {
			// Child Node is what we're looking for.
			if (subNode != null) {
				log.info("<key> Node must not contain more than one value sub-Node " + keyEle);
			} else {
				subNode = node;
			}
		}
		if (subNode == null) {
			return null;
		}
		return parsePropertySubNode(subNode, bd, defaultKeyTypeName);
	}

	/**
	 * Parse a props Node.
	 */
	public Object parsePropsNode(Node propsEle) {
		Map props = new HashMap<>();
		props.put(ATTR_ROOT, this.root);
		props.put(ATTR_MERGE, parseMergeAttribute(propsEle));
		List<Node> propEles = DomUtils.getChildElementsByTagName(propsEle, DOC_PROP);
		for (Node propEle : propEles) {
			String key = propEle.attrString(ATTR_KEY);
			// Trim the text value to avoid unwanted whitespace
			// caused by typical XML formatting.
			String value = DomUtils.getTextValue(propEle).trim();
			props.put(key, value);
		}

		return props;
	}

	/**
	 * Parse the merge attribute of a collection Node, if any.
	 */
	public boolean parseMergeAttribute(Node collectionNode) {
		String value = collectionNode.attrString(ATTR_MERGE);
		if (DEFAULT_VALUE.equals(value)) {
			String df = this.root.attrString(ATTR_DEFAULT_MERGE);
			if (StringUtils.isNotEmpty(df)) {
				value = df;
			} else {
				value = FALSE_VALUE;
			}
		}
		return TRUE_VALUE.equals(value);
	}

	public BeanDefinition parseCustomNode(Node ele) {
		return parseCustomNode(ele, null);
	}

	public BeanDefinition parseCustomNode(Node ele, BeanDefinition containingBd) {
		return this.handler.parse(ele, containingBd, root);
	}

	public BeanDefinition decorateBeanDefinitionIfRequired(Node ele, BeanDefinition bd) {
		// Decorate based on custom attributes first.
		Map<String, Object> attributes = ele.attrs();
		BeanDefinition finalDefinition = bd;
		HandlerProxy.decorate(attributes, finalDefinition, this.root);
		// Decorate based on custom nested Nodes.
		List<Node> childs = ele.getChilds();
		for (Node node : childs) {
			HandlerProxy.decorate(node, finalDefinition, this.root);
		}
		return finalDefinition;
	}

	private BeanDefinition parseNestedCustomNode(Node ele, BeanDefinition containingBd) {
		BeanDefinition innerDefinition = parseCustomNode(ele, containingBd);
		if (innerDefinition == null) {
			log.info("Incorrect usage of Node '" + ele.getName() + "' in a nested manner. "
					+ "This tag cannot be used nested inside <property>. " + ele);
			return null;
		}
		String id = ele.getName() + BeanUtils.GENERATED_BEAN_NAME_SEPARATOR
				+ ObjectUtils.getIdentityHexString(innerDefinition);
		innerDefinition.setBeanName(id);
		return innerDefinition;
	}

	/**
	 * Determine whether the name of the supplied node is equal to the supplied
	 * name.
	 * <p>
	 * The default implementation checks the supplied desired name against both
	 * {@link Node#getNodeName()} and {@link Node#getLocalName()}.
	 * <p>
	 * Subclasses may override the default implementation to provide a different
	 * mechanism for comparing node names.
	 * 
	 * @param node        the node to compare
	 * @param desiredName the name to check for
	 */
	public boolean nodeNameEquals(Node node, String desiredName) {
		return desiredName.equals(node.getName());
	}

	public boolean isDefaultNamespace(Node node) {
		return node.getName().equals(Constant.DOC_BEAN) || node.getName().equals(Constant.DOC_ROOT)
				|| node.getName().equals(Constant.DOC_IMPORT);

	}

}
