function showCaseDetails(name, age, missingDate, description, imageUrl) {
    document.getElementById('caseName').textContent = name;
    document.getElementById('caseAge').textContent = age;
    document.getElementById('caseMissingDate').textContent = missingDate;
    document.getElementById('caseDescription').textContent = description;
    document.getElementById('caseImage').src = imageUrl;

    // Bootstrap 모달 인스턴스를 생성하여 모달 표시
    var caseDetailsModal = new bootstrap.Modal(document.getElementById('caseDetailsModal'));
    caseDetailsModal.show();
}
