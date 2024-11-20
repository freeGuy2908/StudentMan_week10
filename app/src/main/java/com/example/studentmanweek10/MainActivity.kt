package com.example.studentmanweek10

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // khoi tao adapter
        studentAdapter = StudentAdapter(
            students,
            onEdit = { position -> showEditStudentDialog(position) },
            onDelete = { position -> deleteStudent(position) }
        )

        // tao RecyclerView
        findViewById<RecyclerView>(R.id.recycler_view_students).apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // button them sinh vien
        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddStudentDialog()
        }
    }

    // ham xu ly dialog them sinh vien
    private fun showAddStudentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.student_dialog, null)
        val nameInput = dialogView.findViewById<TextInputEditText>(R.id.input_student_name)
        val idInput = dialogView.findViewById<TextInputEditText>(R.id.input_student_id)

        AlertDialog.Builder(this)
            .setTitle("Thêm Sinh Viên")
            .setView(dialogView)
            .setPositiveButton("Lưu") { _, _ ->
                val name = nameInput.text.toString()
                val id = idInput.text.toString()
                if (name.isNotBlank() && id.isNotBlank()) {
                    students.add(StudentModel(name, id))
                    studentAdapter.notifyItemInserted(students.size - 1)
                    Toast.makeText(this, "Đã thêm sinh viên!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    // ham xu ly dialog sua sinh vien
    private fun showEditStudentDialog(position: Int) {
        val student = students[position]
        val dialogView = layoutInflater.inflate(R.layout.student_dialog, null)
        val nameInput = dialogView.findViewById<TextInputEditText>(R.id.input_student_name)
        val idInput = dialogView.findViewById<TextInputEditText>(R.id.input_student_id)

        nameInput.setText(student.studentName)
        idInput.setText(student.studentId)

        AlertDialog.Builder(this)
            .setTitle("Sửa Sinh Viên")
            .setView(dialogView)
            .setPositiveButton("Cập Nhật") { _, _ ->
                val newName = nameInput.text.toString()
                val newId = idInput.text.toString()
                if (newName.isNotBlank() && newId.isNotBlank()) {
                    students[position] = StudentModel(newName, newId)
                    studentAdapter.notifyItemChanged(position)
                    Toast.makeText(this, "Đã cập nhật thông tin!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    // ham xu ly xoa sinh vien va hoan tac sinh vien
    private fun deleteStudent(position: Int) {
        val deletedStudent = students[position]
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)

        Snackbar.make(
            findViewById(R.id.main),
            "Đã xóa sinh viên ${deletedStudent.studentName}",
            Snackbar.LENGTH_LONG
        ).setAction("Hoàn tác") {
            students.add(position, deletedStudent)
            studentAdapter.notifyItemInserted(position)
        }.show()
    }
}