# This file is meant to be run inside lldb as a command after
# the attach_linux.dylib dll has already been loaded to settrace for all threads.
def __lldb_init_module(debugger, internal_dict):
    # Command Initialization code goes here
    print('Startup LLDB in Python!')
    import lldb

    try:
        show_debug_info = 1
        is_debug = 0
        target = debugger.GetSelectedTarget()
        if target:
            process = target.GetProcess()
            if process:
                for thread in process:
                    # Get the first frame
                    print('Thread %s, suspended %s\n'%(thread, thread.IsStopped()))

                    process.SetSelectedThread(thread)

                    if not thread.IsStopped():
                        error = process.Stop()
                        print(error)

                    if thread:
                        frame = thread.GetSelectedFrame()
                        if frame:
                            print('Will settrace in: %s' % (frame,))
                            res = frame.EvaluateExpression("(int) SetSysTraceFunc(%s, %s)" % (
                                show_debug_info, is_debug), lldb.eDynamicCanRunTarget)
                            error = res.GetError()
                            if error:
                                print(error)
                    thread.Resume()
    except:
        import traceback;traceback.print_exc()
