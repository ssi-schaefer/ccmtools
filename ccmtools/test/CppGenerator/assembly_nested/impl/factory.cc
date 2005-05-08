template<class T>
class ImplFactory : public ImplBaseFactory {
public:
  Object* create(const Model&,const WX::EntityContainer::EntityReference&);
};


template<class T> inline
Object* 
ImplFactory<T>::create(const Model& rModel, 
		       const WX::EntityContainer::EntityReference& dbRef) {
  T* pT = new T();
  pT->setDbRef(dbRef);
  pT->setModel(&rModel);
  return pT;
}
