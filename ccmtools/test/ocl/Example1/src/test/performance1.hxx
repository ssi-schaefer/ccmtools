// $Id$

{
    SmartPtr<IntegerStack> theStack = myMath->provide_stack();
    clock_t clockStart = clock();
    for( int counter=0; counter<1000; counter++ )
    {
        int index;
        for( index=0; index<1000; index++ )
        {
            theStack->push(index);
        }
        for( index=0; index<1000; index++ )
        {
            long dummy = theStack->pop();
        }
    }
    clock_t clockEnd = clock();
    double cpu_time_used = double(clockEnd-clockStart) / CLOCKS_PER_SEC * 1000.0;
    cout << "  time: " << cpu_time_used << "ms" << endl;
}
