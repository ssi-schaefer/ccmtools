//==============================================================================
// local transaction header file
//==============================================================================

#ifndef __LOCAL__TRANSACTION__H__
#define __LOCAL__TRANSACTION__H__

namespace localTransaction {

  class NoTransaction {};
  class NotSupported {};
  class SystemError {};
  class RollbackError {};
  class HeuristicMixed {};
  class HeuristicRollback {};
  class Security {};
  class InvalidToken {};

  enum Status {
    ACTIVE,
    MARKED_ROLLBACK,
    PREPARED,
    COMMITTED,
    ROLLED_BACK,
    NO_TRANSACTION,
    PREPARING,
    COMMITTING,
    ROLLING_BACK
  };

  typedef std::vector<unsigned char> TranToken;

  /***
   * A component specifying self-managed transactions may use the CORBA
   * transaction service directly to manipulate the current transaction; or it
   * may choose to use a simpler API (UserTransaction), which exposes only those
   * transaction demarcation functions needed by the component implementation.
   * CCM Spec. 4-23
   ***/
  class UserTransaction {
  public:
    virtual ~UserTransaction (  ) {}

    virtual void begin (  )
      throw ( NotSupported, SystemError ) = 0;

    virtual void commit (  )
      throw ( RollbackError, NoTransaction, HeuristicMixed, HeuristicRollback, Security, SystemError ) = 0;

    virtual void rollback (  )
      throw ( NoTransaction, Security, SystemError ) = 0;

    virtual void set_rollback_only (  )
      throw ( NoTransaction, SystemError ) = 0;

    virtual Status get_status (  )
      throw ( SystemError ) = 0;

    virtual void set_timeout ( const long to )
      throw ( SystemError ) = 0;

    virtual TranToken& suspend (  )
      throw ( NoTransaction, SystemError ) = 0;

    virtual void resume ( const TranToken& txtoken )
      throw ( InvalidToken, SystemError ) = 0;
  };

} // /namespace localTransaction


#endif // __LOCAL__TRANSACTION__H__


