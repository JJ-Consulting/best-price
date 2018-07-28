import {NgModule} from "@angular/core";
import {
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatCardModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule,
  MatIconModule,
  MatDividerModule,
  MatListModule,
  MatMenuModule
} from "@angular/material";


@NgModule({
  imports: [
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatCardModule,
    MatSnackBarModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
    MatMenuModule
  ],
  exports: [
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatCardModule,
    MatSnackBarModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
    MatMenuModule
  ]
})
export class MaterialModule { }
