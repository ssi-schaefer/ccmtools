#include "DebugWriterManager.h"

namespace CCM_Utils {

DebugWriterManager* DebugWriterManager::inst_ = 0;

DebugWriterManager::DebugWriterManager
  (  )
{
  defaultWriter_  = &CerrDebugWriter::instance (  );
  debugWriter_    = defaultWriter_;
  explicitWriter_ = 0;
}

DebugWriterManager&
DebugWriterManager::instance
  (  )
{
  if ( inst_ == 0 ) { inst_ = new DebugWriterManager (  ); }
  return *inst_;
}

void
DebugWriterManager::setDebugWriter
  ( DebugWriter* debWriter )
{ explicitWriter_ = debugWriter_ = debWriter; }

DebugWriter&
DebugWriterManager::getDebugWriter
  (  )
{ return *debugWriter_; }

void
DebugWriterManager::activate
  (  )
{ if ( explicitWriter_ != 0 ) { debugWriter_ = explicitWriter_; } }

void
DebugWriterManager::deactivate
  (  )
{ debugWriter_ = defaultWriter_; }

} // /namespace CCM_Utils


