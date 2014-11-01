/**
 * 
 */
package org.sample.web.models;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

/**
 * @author mpostelnicu
 *
 */
public class SubComponentWrapModel<T> implements IWrapModel<T> {
	
	private static final long serialVersionUID = 1L;
	private Component parent;
	
	public SubComponentWrapModel(Component parent) {
		this.parent=parent;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IWrapModel#getWrappedModel()
	 */
	@Override
	public IModel<?> getWrappedModel() {
		return parent.getDefaultModel();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getObject() {
		return (T) parent.getDefaultModelObject();
	}

	@Override
	public void setObject(T object) {
		parent.setDefaultModelObject(object);
	}

	@Override
	public void detach() {
		IModel<?> wrappedModel = getWrappedModel();
		if(wrappedModel!=null) wrappedModel.detach();
	}

}
