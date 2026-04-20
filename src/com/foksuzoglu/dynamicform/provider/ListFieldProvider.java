package com.foksuzoglu.dynamicform.provider;

//public class ListFieldProvider implements FieldComponentProvider {
//
//	@Override
//	public boolean supports(Field field) {
//		return List.class.isAssignableFrom(field.getType());
//	}
//
//	@Override
//    public Object getValue(JComponent component, Class<?> targetType) {
//
//        ListPanel panel = (ListPanel) component;
//        List<Object> list = new ArrayList<>();
//
//        for (Component wrapper : panel.getPanelList().getComponents()) {
//
//            if (!(wrapper instanceof JPanel row)) {
//				continue;
//			}
//
//            Object item = ... // row'u object'e çevir (recursive)
//
//            list.add(item);
//        }
//
//        return list;
//    }
//
//	@Override
//	public void setValue(JComponent component, Object value) {
//		// burayı sonra doldur
//	}
//}