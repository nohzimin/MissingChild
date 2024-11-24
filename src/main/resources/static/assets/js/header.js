
    document.addEventListener('DOMContentLoaded', function() {
    const dropdownToggles = document.querySelectorAll('.dropdown-toggle');

    dropdownToggles.forEach(toggle => {
    toggle.addEventListener('click', function(e) {
    e.stopPropagation(); // 이벤트 버블링 방지
    const dropdown = this.closest('.nav-dropdown');
    const menu = dropdown.querySelector('.dropdown-menu');
    const isOpen = menu.classList.contains('show');

    // 다른 모든 드롭다운 메뉴 닫기
    document.querySelectorAll('.dropdown-menu').forEach(menu => {
    menu.classList.remove('show');
});
    document.querySelectorAll('.dropdown-toggle').forEach(toggle => {
    toggle.classList.remove('active');
});

    // 현재 드롭다운 토글
    if (!isOpen) {
    menu.classList.add('show');
    this.classList.add('active');
    }
});
});

    // 문서의 다른 부분 클릭시 드롭다운 닫기
    document.addEventListener('click', function(e) {
    if (!e.target.closest('.nav-dropdown')) {
    document.querySelectorAll('.dropdown-menu').forEach(menu => {
    menu.classList.remove('show');
});
    document.querySelectorAll('.dropdown-toggle').forEach(toggle => {
    toggle.classList.remove('active');
});
}
});

    // 드롭다운 메뉴 내부 클릭시 이벤트 전파 중단
    document.querySelectorAll('.dropdown-menu').forEach(menu => {
    menu.addEventListener('click', function(e) {
    e.stopPropagation();
});
});
});