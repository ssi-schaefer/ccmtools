// $Id$

{
    cout << "  " << LOOP1 << " loops with " << LOOP2 << " push/pop calls" << endl;
    clock_t clockStart = clock();
    for( int counter=0; counter<LOOP1; counter++ )
    {
        int index;
        for( index=0; index<LOOP2; index++ )
        {
            theStack->push(index);
        }
        for( index=0; index<LOOP2; index++ )
        {
            long dummy = theStack->pop();
        }
    }
    clock_t clockEnd = clock();
    double cpu_time_used = double(clockEnd-clockStart) / CLOCKS_PER_SEC * 1000.0;
    cout << "  time: " << cpu_time_used << "ms" << endl;
}
