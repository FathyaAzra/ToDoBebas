package com.example.todobebas

class KalenderActivity {

        /* private var _binding: FragmentKalenderBinding? = null
        private val binding get() = _binding!!

        private val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        private lateinit var taskListAdapter: TaskListAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Initialize ViewModel
            val kalenderViewModel =
                ViewModelProvider(this).get(KalenderViewModel::class.java)

            // Inflate layout with ViewBinding
            _binding = FragmentKalenderBinding.inflate(inflater, container, false)
            val root: View = binding.root

            // Set up the RecyclerView for tasks
            val taskRecyclerView: RecyclerView = binding.taskList
            taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            taskListAdapter = TaskListAdapter(emptyList()) // Initialize with an empty list
            taskRecyclerView.adapter = taskListAdapter

            // Set up CalendarView listener
            val calendarView: CalendarView = binding.calendarView
            val tasksTextView: TextView = binding.tasksTextView

            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val selectedDate = dateFormatter.format(
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("$year-${month + 1}-$dayOfMonth")!!
                )
                // Update the tasks list based on selected date
                tasksTextView.text = "Tasks for $selectedDate"

                // Here, you can update the task list for the selected date
                kalenderViewModel.getTasksForDate(selectedDate).observe(viewLifecycleOwner) { tasks ->
                    taskListAdapter.updateTaskList(tasks)
                }
            }

            return root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null // Nullify binding to avoid memory leaks
        } */

    }