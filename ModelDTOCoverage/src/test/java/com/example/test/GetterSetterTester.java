package com.example.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.sql.Timestamp;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

/**
 * A Utility class to use getters and setters with reflection API
 */
public class GetterSetterTester {
 
	 
	 /** A map of default mappers for common objects */
    private static final ImmutableMap<Class<?>, Supplier<?>> DEFAULT_MAPPERS;
	
	static {
        final Builder<Class<?>, Supplier<?>> mapperBuilder = ImmutableMap.builder();
        /* Primitives */
        mapperBuilder.put(int.class, () -> 0);
        mapperBuilder.put(double.class, () -> 0.0d);
        mapperBuilder.put(float.class, () -> 0.0f);
        mapperBuilder.put(long.class, () -> 0l);
        mapperBuilder.put(boolean.class, () -> true);
        mapperBuilder.put(short.class, () -> (short) 0);
        mapperBuilder.put(byte.class, () -> (byte) 0);
        mapperBuilder.put(char.class, () -> (char) 0);

        mapperBuilder.put(Integer.class, () -> Integer.valueOf(0));
        mapperBuilder.put(Double.class, () -> Double.valueOf(0.0));
        mapperBuilder.put(Float.class, () -> Float.valueOf(0.0f));
        mapperBuilder.put(Long.class, () -> Long.valueOf(0));
        mapperBuilder.put(Boolean.class, () -> Boolean.TRUE);
        mapperBuilder.put(Short.class, () -> Short.valueOf((short) 0));
        mapperBuilder.put(Byte.class, () -> Byte.valueOf((byte) 0));
        mapperBuilder.put(Character.class, () -> Character.valueOf((char) 0));

        mapperBuilder.put(BigDecimal.class, () -> BigDecimal.ONE);
        mapperBuilder.put(Date.class, () -> new Date(System.currentTimeMillis()));
        mapperBuilder.put(Timestamp.class, () -> new Timestamp(System.currentTimeMillis()));

        /* Collection Types. */
        mapperBuilder.put(Set.class, () -> Collections.emptySet());
        mapperBuilder.put(SortedSet.class, () -> Collections.emptySortedSet());
        mapperBuilder.put(List.class, () -> Collections.emptyList());
        mapperBuilder.put(Map.class, () -> Collections.emptyMap());
        mapperBuilder.put(SortedMap.class, () -> Collections.emptySortedMap());
        mapperBuilder.put(String[].class, () -> new String[0]);
        mapperBuilder.put(Integer[].class, () -> new Integer[0]);
        
        DEFAULT_MAPPERS = mapperBuilder.build();
		}
	
	 /**
     * A custom mapper, Normally used for the test class with abstract objects.
     */
    private final ImmutableMap<Class<?>, Supplier<?>> mappers;
    
    /**
     * Creates an instance with the default ignore fields.
     */
    public GetterSetterTester() {
    	this(null, null);
    }
	
    /**
     * Creates an instance with ignore fields and additional custom mappers.
     * @param customMappers Any custom mappers for a given class type.
     * @param ignoreFields The getters which should be ignored (e.g., "getId" or "isActive").
     */
    private GetterSetterTester(Map<Class<?>, Supplier<?>> customMappers, Set<String> ignoreFields) {
    	if (customMappers == null) {
            this.mappers = DEFAULT_MAPPERS;
        } else {
            final Builder<Class<?>, Supplier<?>> builder = ImmutableMap.builder();
            builder.putAll(customMappers);
            builder.putAll(DEFAULT_MAPPERS);
            this.mappers = builder.build();
        }
    }

	
	/**
	 * Use both setters and getters of the instance
	 * @param Instance of Input object
	 * @param InstanceType
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object getSetDefaultValues(Object instance, String operationType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SortedMap<String, GetterSetterPair> gettersSettersMapping= this.addGettersAndSettersToMap(instance);
		this.setDefaultValuesToInstance(instance, gettersSettersMapping);
		if(null!= operationType && !operationType.isEmpty() && operationType.equalsIgnoreCase("SetGet")) {
			this.getDefaultValuesFromInstance(instance, gettersSettersMapping);
		}
		return instance;
	}
	
	/**
	 * Only set values to instance
	 * @param Instance of Input object
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object getSetDefaultValues(Object instance) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SortedMap<String, GetterSetterPair> gettersSettersMapping= this.addGettersAndSettersToMap(instance);
		this.setDefaultValuesToInstance(instance, gettersSettersMapping);
		return instance;
	}
	
	 /**
	 * @param Instance of Input object
	 * @param Map with all getters and Setters
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void getDefaultValuesFromInstance(Object instance, SortedMap<String, GetterSetterPair> gettersSettersMapping) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		 for (final Entry<String, GetterSetterPair> entry : gettersSettersMapping.entrySet()) {
				final GetterSetterPair pair = entry.getValue();
				if (pair.hasGetterAndSetter()) {
	                pair.getGetter().invoke(instance);
				}
		 }
	}

	/**
	 * @param Instance of Input object
	 * @param Map with all getters and Setters
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	private void setDefaultValuesToInstance(Object instance,SortedMap<String, GetterSetterPair> gettersSettersMapping) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			for (final Entry<String, GetterSetterPair> entry : gettersSettersMapping.entrySet()) {
				final String objectName = entry.getKey();
		        final String fieldName = objectName.substring(0, 1).toLowerCase() + objectName.substring(1);
				final GetterSetterPair pair = entry.getValue();
				if (pair.hasGetterAndSetter()) {
					final Class<?> parameterType = pair.getSetter().getParameterTypes()[0];
	                final Object newObject = createObject(fieldName, parameterType);
	                pair.getSetter().invoke(instance, newObject);
				}
		  }
	}

	/**
     * Creates an object for the given Datatype
     * @param The name of the field
     * @param clazz type to create
     * @return new instance for Datatype
     * @throws InstantiationException
     * @throws IllegalAccessException If the class or its nullary constructor is not accessible
     *
     */
    private Object createObject(String fieldName, Class<?> clazz)
            throws InstantiationException, IllegalAccessException {
      try {      
          final Supplier<?> supplier = this.mappers.get(clazz);
          if (supplier != null) {
              return supplier.get();
          }
          return clazz.newInstance();        
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Unable to create objects for field '" + fieldName + "'.", e);
        }
    }

    /**
     * @param Instance of Input object
     * @return Map with getters and setters of input instance
     */
    private SortedMap<String, GetterSetterPair> addGettersAndSettersToMap(Object inputObject) {
    	final SortedMap<String, GetterSetterPair> getterSetterMapping = new TreeMap<>();
    	 for (final Method method : inputObject.getClass().getMethods()) {
             final String methodName = method.getName();
             String objectName;
             if (methodName.startsWith("get") && method.getParameters().length == 0) {
                 /* Found the get method. */
                 objectName = methodName.substring("get".length());
                 GetterSetterPair getterSetterPair = getterSetterMapping.get(objectName);
                 if (getterSetterPair == null) {
                	 getterSetterPair = new GetterSetterPair();
                     getterSetterMapping.put(objectName, getterSetterPair);
                 }
                 getterSetterPair.setGetter(method);
             } else if (methodName.startsWith("set") && method.getParameters().length == 1) {
                 /* Found the set method. */
                 objectName = methodName.substring("set".length());
                 GetterSetterPair getterSetterPair = getterSetterMapping.get(objectName);
                 if (getterSetterPair == null) {
                	 getterSetterPair = new GetterSetterPair();
                     getterSetterMapping.put(objectName, getterSetterPair);
                 }
                 getterSetterPair.setSetter(method);
             } else if (methodName.startsWith("is") && method.getParameters().length == 0) {
                 /* Found the is method, which really is a get method. */
                 objectName = methodName.substring("is".length());
                 GetterSetterPair getterSetterPair = getterSetterMapping.get(objectName);
                 if (getterSetterPair == null) {
                	 getterSetterPair = new GetterSetterPair();
                     getterSetterMapping.put(objectName, getterSetterPair);
                 }
                 getterSetterPair.setGetter(method);
             }
         }
    	return getterSetterMapping;
    }
    
}
