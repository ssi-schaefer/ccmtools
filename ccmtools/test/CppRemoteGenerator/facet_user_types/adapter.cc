
/**
 * IDL: string foo1(in string s1, inout string s2, out string s3); 
 * 
 **/
char* consoleFacetAdapter::foo1(const char* s1, char*& s2, CORBA::String_out s3) 
{
  DEBUGNL(" consoleFacetAdapter::foo1()");

  // %(MParameterDefConvertParameter)s
  std::string parameter_s1 = CCM::CORBAPK_STRING_to_PK_STRING(s1);  // IN
  std::string parameter_s2 = CCM::CORBAPK_STRING_to_PK_STRING(s2);  // INOUT
  std::string parameter_s3; // OUT

  // %(MParameterDefDeclareResult)s
  std::string result;

  try {
    // %(MParameterDefConvertMethod)s
   result = local_adapter->foo1( parameter_s1,  parameter_s2,  parameter_s3);
  }
  // %(MParameterDefConvertExceptions)s 
  catch(...) {
    throw CORBA::SystemException();	
  }

  // %(MParameterDefConvertResult)s
  s2= CCM::PK_STRING_to_CORBAPK_STRING(parameter_s2);
  s3= CCM::PK_STRING_to_CORBAPK_STRING(parameter_s3);

  return CCM::PK_STRING_to_CORBAPK_STRING(result);
}


/**
 * IDL: Person  foo3(in Person p1, inout Person p2, out Person p3);
 *
 **/
Person* consoleFacetAdapter::foo3(const Person& p1, Person& p2, Person_out p3) 
{
  DEBUGNL(" consoleFacetAdapter::foo3()");

  // %(MParameterDefConvertParameter)s
  CCM_Local::Person parameter_p1; // IN
  parameter_p1.id = CCM::CORBAPK_LONG_to_PK_LONG(p1.id);
  parameter_p1.name = CCM::CORBAPK_STRING_to_PK_STRING(p1.name);

  CCM_Local::Person parameter_p2; // INOUT
  parameter_p2.id = CCM::CORBAPK_LONG_to_PK_LONG(p2.id); 
  parameter_p2.name = CCM::CORBAPK_STRING_to_PK_STRING(p2.name);

  CCM_Local::Person parameter_p3; // OUT

  // %(MParameterDefDeclareResult)s
  CCM_Local::Person result;

  try {
    // %(MParameterDefConvertMethod)s
    result = local_adapter->foo3(parameter_p1,  parameter_p2,  parameter_p3);
  }
  // %(MParameterDefConvertExceptions)s  
  catch(...) {
    throw CORBA::SystemException();     
  }

  // %(MParameterDefConvertParameterToCorba)s
  p2.id = CCM::PK_LONG_to_CORBAPK_LONG(parameter_p2.id);
  p2.name = CCM::PK_STRING_to_CORBAPK_STRING(parameter_p2.name); 
  
  p3 = new Person;
  p3->id = CCM::PK_LONG_to_CORBAPK_LONG(parameter_p3.id);
  p3->name = CCM::PK_STRING_to_CORBAPK_STRING(parameter_p3.name);

  // %(MParameterDefConvertResult)s
  Person_var return_value = new Person;
  return_value->id = CCM::PK_LONG_to_CORBAPK_LONG(result.id);
  return_value->name = CCM::PK_STRING_to_CORBAPK_STRING(result.name);

  return return_value._retn();
}



/**
 * IDL: longList foo2(in longList l1, inout longList l2, out longList l3);
 *
 **/
longList* consoleFacetAdapter::foo2(const longList& l1, longList& l2, longList_out l3) 
{
  DEBUGNL(" consoleFacetAdapter::foo2()");

  // %(MParameterDefConvertParameter)s
  CCM_Local::longList parameter_l1; // IN
  for(unsigned long i=0; i< l1.length(); i++) {
    parameter_l1.push_back(CCM::CORBAPK_LONG_to_PK_LONG(l1[i]));
  }
  CCM_Local::longList parameter_l2; // INOUT
  for(unsigned long i=0; i< l2.length(); i++) {
    parameter_l2.push_back(CCM::CORBAPK_LONG_to_PK_LONG(l2[i]));
  }
  CCM_Local::longList parameter_l3; // OUT

  // %(MParameterDefDeclareResult)s
  CCM_Local::longList result;
  try {
    // %(MParameterDefConvertMethod)s
    result = local_adapter->foo2( parameter_l1,  parameter_l2,  parameter_l3);
  }
  // %(MParameterDefConvertExceptions)s  
  catch(...) {
    throw CORBA::SystemException();	
  }

  // %(MParameterDefConvertParameterToCorba)s
  l2.length(parameter_l2.size());  // INOUT
  for(unsigned long i=0; i<parameter_l2.size();i++) {
    l2[i] = CCM::PK_LONG_to_CORBAPK_LONG(parameter_l2[i]);
  }

  l3 = new longList;
  l3->length(parameter_l3.size()); // OUT
  for(unsigned long i=0; i<parameter_l3.size();i++) {
    (*l3)[i] = CCM::PK_LONG_to_CORBAPK_LONG(parameter_l3[i]);
  }
  
  // %(MParameterDefConvertResult)s  
  longList_var return_value = new longList;  
  return_value->length(result.size());
  for(unsigned long i=0; i< result.size();i++) {
    (*return_value)[i] = CCM::PK_LONG_to_CORBAPK_LONG(result[i]);
  }
  return return_value._retn();
}


PersonMap* consoleFacetAdapter::foo4(const PersonMap& p1, PersonMap& p2, PersonMap_out p3) 
{
  DEBUGNL(" consoleFacetAdapter::foo4()");

  // %(MParameterDefConvertParameter)s
  // IN
  CCM_Local::PersonMap parameter_p1;
  for(unsigned long i=0; i< p1.length(); i++) {
    Person p1_corba_item = p1[i];              // remote struct
    CCM_Local::Person p1_cpp_item;             // local struct
    p1_cpp_item.id = CCM::CORBAPK_LONG_to_PK_LONG(p1_corba_item.id);
    p1_cpp_item.name = CCM::CORBAPK_STRING_to_PK_STRING(p1_corba_item.name);
    parameter_p1.push_back(p1_cpp_item);
  }

  // INOUT
  CCM_Local::PersonMap parameter_p2;
  for(unsigned long i=0; i< p2.length(); i++) {
    Person p2_corba_item = p2[i];              // remote struct
    CCM_Local::Person p2_cpp_item;             // local struct
    p2_cpp_item.id = CCM::CORBAPK_LONG_to_PK_LONG(p2_corba_item.id);
    p2_cpp_item.name = CCM::CORBAPK_STRING_to_PK_STRING(p2_corba_item.name);
    parameter_p2.push_back(p2_cpp_item);
  }

  // OUT
  CCM_Local::PersonMap parameter_p3;

  // %(MParameterDefDeclareResult)s
  CCM_Local::PersonMap result;
  try {
    // %(MParameterDefConvertMethod)s
    result = local_adapter->foo4( parameter_p1,  parameter_p2,  parameter_p3);
  }
  // %(MParameterDefConvertExceptions)s 
  catch(...) {
    throw CORBA::SystemException();	
  }

  // %(MParameterDefConvertResult)s
  // INOUT
  p2.length(parameter_p2.size());
  for(unsigned long i=0; i< parameter_p2.size(); i++) {
    CCM_Local::Person cpp_item = parameter_p2[i]; // local struct
    Person corba_item;                            // remote struct
    corba_item.id   = CCM::PK_LONG_to_CORBAPK_LONG(cpp_item.id);
    corba_item.name = CCM::PK_STRING_to_CORBAPK_STRING(cpp_item.name);
    p2[i] = corba_item;
  }

  // OUT
  p3 = new PersonMap;
  p3->length(parameter_p3.size());
  for(unsigned long i=0; i< parameter_p3.size(); i++) {
  CCM_Local::Person Cpp_item= parameter_p3[i];
  Person_var corba_item = new Person;
  corba_item->id = CCM::PK_LONG_to_CORBAPK_LONG(Cpp_item.id);
  corba_item->name = CCM::PK_STRING_to_CORBAPK_STRING(Cpp_item.name);
  (*p3)[i] = corba_item;
  }

  // %(MParameterDefConvertResult)s  
  PersonMap_var return_value = new PersonMap; 
  return_value->length(result.size());
  for(unsigned long i=0; i< result.size(); i++) {
    CCM_Local::Person cpp_item = result[i];  // local struct
    Person corba_item;                       // remote struct
    corba_item.id = CCM::PK_LONG_to_CORBAPK_LONG(cpp_item.id);
    corba_item.name = CCM::PK_STRING_to_CORBAPK_STRING(cpp_item.name);
    (*return_value)[i] = corba_item;
  }
  return return_value._retn();
}



