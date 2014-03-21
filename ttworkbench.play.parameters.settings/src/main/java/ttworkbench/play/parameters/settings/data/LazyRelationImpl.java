package ttworkbench.play.parameters.settings.data;

import java.util.Arrays;
import java.util.LinkedList;

import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.Data.RelationPartner;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;
import ttworkbench.play.parameters.settings.exceptions.ParameterDefinitionNotFoundException;
import ttworkbench.play.parameters.settings.loader.DataLoaderAbstract;

public class LazyRelationImpl implements Data.Relation {

	
	private class Rel {
		public Rel(String id, boolean msg, boolean act) {
			this.id = id;
			this.msg = msg;
			this.act = act;
		}
		private String id;
		private boolean msg;
		private boolean act;
	}
	private class RelParameter extends Rel {
		public RelParameter(String id, boolean msg, boolean act) {
			super(id, msg, act);
		}
	}
	private class RelWidget extends Rel {
		public RelWidget(String name, boolean msg, boolean act) {
			super(name, msg, act);
		}
	}

	
	
	
	private Data.Validator validator;
	private LinkedList<Rel> relPartners = new LinkedList<Rel>();
	private Data.RelationPartner[] partners = null;
	private DataLoaderAbstract dataLoader;

	public LazyRelationImpl(DataLoaderAbstract dataLoader, Data.Validator validator) {
		this.validator = validator;
		this.dataLoader = dataLoader;
	}
	public Data.Validator getValidator() {
		return validator;
	}

	public Data.RelationPartner[] getRelationPartners() {
		if(partners==null) {
			LinkedList<Data.RelationPartner> partnersList = new LinkedList<Data.RelationPartner>();
			try {
				for(Rel rel : relPartners) {
					final boolean thisRegisteredForMessages = rel.msg;
					final boolean thisRegisteredForActions = rel.act;
					final Data.Partner thisPartner = (rel instanceof RelParameter ? dataLoader.getParameter(rel.id) : dataLoader.getWidget(rel.id));

					partnersList.add(new RelationPartner() {
						
						public boolean isRegisteredForMessages() {
							return thisRegisteredForMessages;
						}
						
						public boolean isRegisteredForActions() {
							return thisRegisteredForActions;
						}
						
						public Data.Partner getPartner() {
							return thisPartner;
						}
					});
				}
			} catch (ParameterDefinitionNotFoundException e) {
				try {
					dataLoader.addError(e);
				} catch (ParameterConfigurationException e1) {
					System.err.println("Ignored error: "+e.getMessage());
				}
			}
			partners = partnersList.toArray(new Data.RelationPartner[0]);
		}
		return partners;
	}
	
	public void addRelatedParameter(String parId, boolean parMsg, boolean parAct) {
		relPartners.add(new RelParameter(parId, parMsg, parAct));
	}
	
	public void addRelatedWidget(String widgetName, boolean parMsg, boolean parAct) {
		relPartners.add(new RelWidget(widgetName, parMsg, parAct));
	}
	
	public int getNumParametersRelated() {
		return relPartners.size();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(partners);
		result = prime * result
				+ ((relPartners == null) ? 0 : relPartners.hashCode());
		result = prime * result
				+ ((validator == null) ? 0 : validator.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LazyRelationImpl other = (LazyRelationImpl) obj;
		if (!Arrays.equals(partners, other.partners))
			return false;
		if (relPartners == null) {
			if (other.relPartners != null)
				return false;
		} else if (!relPartners.equals(other.relPartners))
			return false;
		if (validator == null) {
			if (other.validator != null)
				return false;
		} else if (!validator.equals(other.validator))
			return false;
		return true;
	}
	
	
}