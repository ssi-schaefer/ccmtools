
//==============================================================================
// Grfx - business logic class definition
//==============================================================================

#ifndef __COMPONENT_CCM_Local_CCM_Session_Grfx_Grfx_APP__H__
#define __COMPONENT_CCM_Local_CCM_Session_Grfx_Grfx_APP__H__

#include <CCM_Local/CCM_Session_Grfx/Grfx_share.h>

namespace CCM_Local {
namespace CCM_Session_Grfx {


//==============================================================================
// component implementation object
//==============================================================================

class CCM_Grfx_impl
  : public CCM_Grfx
{
 private:


 public:
  CCM_Grfx_Context* ctx;

  CCM_Grfx_impl (  );
  virtual ~CCM_Grfx_impl (  );


  virtual CCM_Point* get_point (  );
  virtual CCM_Line* get_line (  );



  // Callback methods

  virtual void set_session_context ( LocalComponents::SessionContext* ctx )
    throw ( LocalComponents::CCMException );
  virtual void ccm_activate (  )
    throw ( LocalComponents::CCMException );
  virtual void ccm_passivate (  )
    throw ( LocalComponents::CCMException );
  virtual void ccm_remove (  )
    throw ( LocalComponents::CCMException );
};

//==============================================================================
// point - facet adapter implementation class
//==============================================================================

class point_impl
  : public CCM_Point
{
 private:
  CCM_Grfx_impl* component;
  double x_, y_;

 public:
  point_impl ( CCM_Grfx_impl* component_impl );
  virtual ~point_impl (  );

  virtual double x (  );
  virtual void x ( const double value );
  virtual double y (  );
  virtual void y ( const double value );

  virtual void move ( const double dx, const double dy ) ;
#ifdef CCM_TEST_PYTHON
  void call_python_move ( const double dx, const double dy ) ;
#endif

};

//==============================================================================
// line - facet adapter implementation class
//==============================================================================

class line_impl
  : public CCM_Line
{
 private:
  CCM_Grfx_impl* component;
  WX::Utils::SmartPtr<Point> startPt_, endPt_;

 public:
  line_impl ( CCM_Grfx_impl* component_impl );
  virtual ~line_impl (  );

  virtual WX::Utils::SmartPtr<Point> startPt (  );
  virtual void startPt ( const WX::Utils::SmartPtr<Point> value );
  virtual WX::Utils::SmartPtr<Point> endPt (  );
  virtual void endPt ( const WX::Utils::SmartPtr<Point> value );

  virtual void move ( const double dx, const double dy ) ;
#ifdef CCM_TEST_PYTHON
  void call_python_move ( const double dx, const double dy ) ;
#endif

};



} // /namespace CCM_Session_Grfx
} // /namespace CCM_Local


#endif


