Imports System.Drawing.Drawing2D
Imports System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel
Imports System.Data.OleDb
Imports System.Data.SqlClient

Public Class Form1
    Private Const CONNECTION_STRING As String = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=C:\Users\User\Desktop\RevisionVB\RevisionVB.mdb"

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        ' Create a new connection
        Using conn As New OleDbConnection(CONNECTION_STRING)
            Try
                ' Open the connection
                conn.Open()

                ' Create a new data adapter based on the specified query.
                Dim dataAdapter As New OleDbDataAdapter("SELECT * FROM student", conn)

                ' Create a command builder to generate SQL update, insert, and
                ' delete commands based on select command. These are used to
                ' update the database.
                Dim commandBuilder As New OleDbCommandBuilder(dataAdapter)

                ' Populate a new data table and bind it to the BindingSource.
                Dim table As New DataTable()
                table.Locale = System.Globalization.CultureInfo.InvariantCulture
                dataAdapter.Fill(table)

                ' Bind the table to the DataGridView
                DataGridView1.DataSource = table

            Catch ex As Exception
                MessageBox.Show("An error occurred while loading the data: " & ex.Message)
            End Try
        End Using
    End Sub

    Private Sub ReferenceToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles ReferenceToolStripMenuItem.Click
        MessageBox.Show("This Created by HambaliFaris")
    End Sub

    Private Sub btnSD_Click(sender As Object, e As EventArgs) Handles btnSD.Click
        Try
            If String.IsNullOrEmpty(txtFullName.Text) OrElse String.IsNullOrEmpty(cbGender.Text) OrElse String.IsNullOrEmpty(txtPhone.Text) OrElse String.IsNullOrEmpty(cbCourse.Text) OrElse String.IsNullOrEmpty(txtSem.Text) OrElse String.IsNullOrEmpty(txtFee.Text) Then
                MessageBox.Show("Please fill all fields")
                Return
            End If

            Dim name As String
            name = txtFullName.Text

            Dim gender As String
            gender = cbGender.Text

            Dim phone As String
            phone = UCase(txtPhone.Text)

            Dim course As String
            course = cbCourse.Text

            Dim semester As String
            semester = UCase(txtSem.Text)

            Dim fee As String
            fee = UCase(txtFee.Text)

            txtShow.Text = "Name : " + name.ToString + vbNewLine + "Gender : " + gender.ToString +
            vbNewLine + "Phone Number : " + phone.ToString + vbNewLine + "Course : " + course.ToString +
            vbNewLine + "Semester : " + semester.ToString + vbNewLine + "Fee : RM" + fee.ToString

        Catch ex As Exception

        End Try
    End Sub

    Private Sub btnExit_Click(sender As Object, e As EventArgs) Handles btnExit.Click
        Me.Close()
    End Sub

    Private Sub btnSubmit_Click(sender As Object, e As EventArgs) Handles btnSubmit.Click
        Dim Name As String = txtFullName.Text
        Dim Gender As String = cbGender.Text
        Dim Phone As String = txtPhone.Text
        Dim Course As String = cbCourse.Text
        Dim Semester As String = txtSem.Text
        Dim Fee As String = txtFee.Text

        Dim sql As String = "INSERT INTO student ([Name],[Gender],[Phone],[Course],[Semester],[Fee]) VALUES (@Name,@Gender,@Phone,@Course,@Semester,@Fee)"
        Try
            conn.Open()

            cmd = conn.CreateCommand
            cmd.Connection = conn

            cmd.CommandText = sql

            cmd.Parameters.AddWithValue("@Name", Name)
            cmd.Parameters.AddWithValue("@Gender", Gender)
            cmd.Parameters.AddWithValue("@Phone", Phone)
            cmd.Parameters.AddWithValue("@Course", Course)
            cmd.Parameters.AddWithValue("@Semester", Semester)
            cmd.Parameters.AddWithValue("@Fee", Fee)

            cmd.ExecuteNonQuery()

            MessageBox.Show("Data inserted successfully!")
        Catch ex As Exception
            MessageBox.Show("An error occurred: " + ex.Message)
        Finally
            ' Close the connection
            If conn IsNot Nothing AndAlso conn.State = ConnectionState.Open Then
                conn.Close()
            End If
        End Try
    End Sub


    Private Sub DataGridView1_CellContentClick(sender As Object, e As DataGridViewCellEventArgs) Handles DataGridView1.CellContentClick

    End Sub
End Class